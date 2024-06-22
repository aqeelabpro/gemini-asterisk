package com.aws.service.stt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.transcribe.TranscribeClient;
import software.amazon.awssdk.services.transcribe.model.*;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SpeechToTextService {

    private final TranscribeClient transcribeClient;
    private final S3Client s3Client;
    private final String bucketName = "aws-polly-asterisk";

    public SpeechToTextService() {
        String region = "us-east-1";
        this.transcribeClient = TranscribeClient.builder()
                .region(Region.of(region))
                .build();

        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .build();
    }

    public String convertSpeechToText(MultipartFile file) {
        try {
            String jobName = "job-" + UUID.randomUUID();
            File audioFile = convertMultiPartToFile(file);

            // Upload the file to S3
            String s3Key = "audio-files/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
            uploadFileToS3(audioFile, s3Key);

            String s3Uri = "s3://" + bucketName + "/" + s3Key;

            startTranscriptionJob(jobName, s3Uri);

            // Polling for the transcription job to complete
            return waitForTranscription(jobName);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    private void uploadFileToS3(File file, String s3Key) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .build();

        s3Client.putObject(putObjectRequest, file.toPath());
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }

    private void startTranscriptionJob(String jobName, String s3Uri) {
        StartTranscriptionJobRequest request = StartTranscriptionJobRequest.builder()
                .transcriptionJobName(jobName)
                .languageCode(LanguageCode.EN_US)
                .media(Media.builder().mediaFileUri(s3Uri).build())
                .mediaFormat(MediaFormat.WAV)
                .build();

        transcribeClient.startTranscriptionJob(request);
    }

    private String waitForTranscription(String jobName) throws InterruptedException {
        while (true) {
            GetTranscriptionJobRequest getTranscriptionJobRequest = GetTranscriptionJobRequest.builder()
                    .transcriptionJobName(jobName)
                    .build();

            GetTranscriptionJobResponse getTranscriptionJobResponse = transcribeClient.getTranscriptionJob(getTranscriptionJobRequest);
            TranscriptionJob transcriptionJob = getTranscriptionJobResponse.transcriptionJob();

            if (transcriptionJob != null && transcriptionJob.transcriptionJobStatus().equals(TranscriptionJobStatus.COMPLETED)) {
                return transcriptionJob.transcript().transcriptFileUri();
            }

            TimeUnit.SECONDS.sleep(5);
        }
    }
}