# Gemini Asterisk PBX Integration
This application allows users to dial any sip extension and ask any question from Gemini

# The Easiest Way To Test
Just install `Zoiper` and Register a SIP user
- SIP URI: 2000@34.125.220.31
- Password: 94d6a19791656fc64f4e55f7867fc407

After Registering The Account, dial extension 111, and follow the instructions in the recording played during the call to ask any questions  
it has some latency due to the conversion of speech-to-text and text-to-speech using Google.


> **_NOTE:_**  Follow the below steps only if you want to test locally.

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
- Zoiper Installed

# Install FreePBX
- Follow The instructions on [FreePBX Installation on Ubuntu 22.04/23.04](https://blog.piecebyte.com/freepbx-installation-on-ubuntu-22-04-23-04)

# Steps to configure in FreePBX and Java Application
- Goto Config Edit in the Admin tab in FreePBX(you will have to download config edit module)
- Paste the code provided at the end to the `extensions_custom.conf` file, and click `Save` button and the `Apply Config` button at the top
- create `Jar file` from this Application and upload to server
- Create a main folder like `gemini-asterisk`
- Inside `gemini-asterisk` folder, create 2 folders, `config` and `bin`
- Inside the `config` folder, create 2 files application,yml and paste content from this Java Application
- Inside the Same `config` folder, create another file `log4j2.xml` and paste `log4j2.xml` from this Java Application
- Create a log folder, let's say the main folder `gemini-asterisk` is in /home/ubuntu, create log folder in same /home/ubuntu
- You also have to have a `google-auth.json` file and replace its path in the `application.yml` file

# Download All FreePBX modules
- Goto `Admin Tab` as shown in `Figure.1`
- Select the `Updates` from dropdown as shown in `Figure 1.1`
- Click The `Modules Updates` as shown in `Figure 1.2`
- Click `Standard` and `Extended` in there and hit check online button as shown in `Figure 1.2`
- You will get `Download All` button, Press that button and it will download all modules including `Config Edit` module, we require as shown in `Figure 1.2`

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
