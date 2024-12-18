# Gemini Asterisk PBX Integration
This application allows users to dial any sip extension and ask any question from Gemini


# Demo
[![Watch the video](https://img.youtube.com/vi/_2lJGsCXJoc/0.jpg)](https://youtu.be/_2lJGsCXJoc)
# Test Locally
# Clone Repository

```
git clone https://github.com/aqeelabpro/gemini-asterisk
```
```
cd gemini-asterisk
```
open in your favourite editor or IDE

# Prerequisites

- FreePBX installed
- Zoiper/Any other Softphone of your choice(some other popular softphones: linphone, microsip) Installed

# Install Zoiper
Goto https://www.zoiper.com/en/voip-softphone/download/current and choose it according to your own OS

# Install FreePBX
- Follow The instructions on [FreePBX Installation on Ubuntu 22.04/23.04](https://blog.piecebyte.com/freepbx-installation-on-ubuntu-22-04-23-04)

# Steps to configure in FreePBX and Java Application
- Goto Config Edit in the Admin tab in FreePBX(you will have to download config edit module)
- Paste the code provided at the end to the `extensions_custom.conf` file, and click `Save` button and the `Apply Config` button at the top
- create `Jar file` from this Application and upload to server
- Create a main folder like `gemini-asterisk` inside your home dir or whereever you want and also create relevant folders and files in the respective folder as described below
- Inside `gemini-asterisk` folder, create 2 folders, `config` and `bin`
- Inside the `config` folder, create a file `application.yml` and paste content from this Java Application
- Inside the Same `config` folder, create another file `log4j2.xml` and paste `log4j2.xml` from this Java Application
- You also have to have a `google-auth.json` or any other name you want the service account key generated inside google cloud and replace its path in the `application.yml` file, so follow below steps

# Google Cloud Account
You will also need a Google Cloud account.

# Create a Google Cloud Service Account
Follow the guidelines here, https://cloud.google.com/iam/docs/service-accounts-create#console

# Create Google Cloud Service Account Key
Follow the guidelines here, https://cloud.google.com/iam/docs/keys-create-delete

# Download All FreePBX modules
- Goto `Admin Tab` as shown in `Figure.1`
- Select the `Updates` from the dropdown as shown in `Figure 1.1`
- Click The `Modules Updates` as shown in `Figure 1.2`
- Click `Standard` and `Extended` in there and hit the check online button as shown in `Figure 1.2`
- You will get the `Download All` button, Press that button and it will download all modules including the `Config Edit` module, which we require as shown in `Figure 1.2`

### Add Files to `/var/lib/asterisk/sounds/en/`
you also have to create two wav files of 8k sample rate called `ask_question` `interrupt-ai` and copy them to `/var/lib/asterisk/sounds/en/`

you can use any text in the files like `to ask question wait for beep sound and ask a question and press # after completing your question` inside `ask-question.wav` file and add `press any key to interrupt the ai and wait for the beep to ask the question if you don't want an answer to be completed and want to ask other question` inside `interrupt-ai.wav`


| ![Figure 1.1](https://github.com/aqeelabpro/gemini-asterisk/assets/93031839/1094e414-5b33-4ff3-a26b-0947ff4f667f "Figure 1.1") | 
|:--:| 
| *Figure 1.1* |

| ![Figure 1.2](https://github.com/aqeelabpro/gemini-asterisk/assets/93031839/85582d37-838a-407c-9a5d-29d0357d3c32 "Figure 1.2") | 
|:--:| 
| *Figure 1.2* |
### extensions_custom.conf Code
```

[google-speech]
exten => 111,1,NoOp(============ ${CONTEXT} =============)
exten => 111,n,Set(__AGI_SERVER_IP=127.0.0.1)
exten => 111,n,Set(__AGI_SERVER_PORT=9000)
exten => 111,n,Set(__AGI_SERVER=agi://${AGI_SERVER_IP}:${AGI_SERVER_PORT})
exten => 111,n,Set(__SOUND_FOLDER=/var/lib/asterisk/sounds/en)
exten => 111,n,Set(__LANG_CODE=en-GB)
exten => 111,n,Set(__LANG_NAME=en-GB-Wavenet-F)
exten => 111,n,Goto(record-answer,111,1)

exten => h,1,Goto(grace_fully_hangup,s,1)

[record-answer]
exten => 111,1,NoOp(=========== ${CONTEXT} ===========)
exten => 111,n,Set(__RECORDING_FILE_NAME=${EPOCH})
; ADDED THIS LINE
exten => 111,n,Playback(ask_question&interrupt-ai)
exten => 111,n(record),Record(${SOUND_FOLDER}/${RECORDING_FILE_NAME}:wav,8,300)
exten => 111,n,AGI(${AGI_SERVER}/speechToText.agi)
exten => 111,n,Goto(play-prompt,111,1)

exten => h,1,Goto(grace_fully_hangup,s,1)

[play-prompt]
exten => 111,1,NoOp(=========== ${CONTEXT} ===========)
exten => 111,n,AGI(${AGI_SERVER}/textToSpeech.agi)
exten => 111,n,Wait(1)
exten => 111,n,Background(${SOUND_FOLDER}/${ANSWER_FILE})
exten => 111,n,Goto(record-answer,${EXTEN},record)

exten => _X,1,Goto(record-answer,111,record)

exten => h,1,Goto(grace_fully_hangup,s,1)

[grace_fully_hangup]
exten => s,1,NoOp(=========== ${CONTEXT} ===========)
exten => s,n,StopMonitor()
exten => s,n,Hangup
```


#### Asterisk Manager Interface(AMI)
Learn more about AMI at https://docs.asterisk.org/Configuration/Interfaces/Asterisk-Manager-Interface-AMI/

#### Asterisk Manager Interface(AMI) Libraries in different languages
https://docs.asterisk.org/Configuration/Interfaces/Asterisk-Manager-Interface-AMI/AMI-Libraries-and-Frameworks/
