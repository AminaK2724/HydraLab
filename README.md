<h1 align="center">Hydra Lab</h1>
<p align="center">Build your own cloud testing infrastructure</p>
<div align="center">

[中文(完善中)](README.zh-CN.md)

[![Build Status](https://dlwteam.visualstudio.com/Next/_apis/build/status/HydraLab-CI?branchName=main)](https://dlwteam.visualstudio.com/Next/_build/latest?definitionId=743&branchName=main)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-v2.2.5-blue)
![Appium](https://img.shields.io/badge/Appium-v8.0.0-yellow)
![License](https://img.shields.io/badge/license-MIT-green)
![visitors](https://visitor-badge.glitch.me/badge?page_id=microsoft.hydralab&left_color=gray&right_color=red)

---

https://github.com/microsoft/HydraLab/assets/8344245/cefefe24-4e11-4cc7-a3af-70cb44974735

[What is Hydra Lab?](#what-is) | [Get Started](#get-started) | [Who are using Hydra Lab?](#who-use-it) | [Contribute](#contribute) | [Contact Us](#contact) | [Links](#links) | [Wiki](https://github.com/microsoft/HydraLab/wiki)
</div>



<span id="what-is"></span>
## What is Hydra Lab?

Hydra Lab is a framework that can help you easily build a cloud testing platform utilizing the test devices/machines in hand. 
It enables dev team to quickly build a self-manageable and intelligent cloud testing infrastructure. With the help of Hydra Lab, you can:

- Either: Create a new cloud testing network.
- Or: Onboard your test device to an existing network with a minimized effort.

Capabilities of Hydra Lab include:
- Scalable test device management under the center-agent distributed design; Test task management and test result visualization.
- Powering [Android Espresso Test](https://developer.android.com/training/testing/espresso).
- Appium(Java) test on different platforms: Windows/iOS/Android/Browser/Cross-platform.
- Case-free test automation: Monkey test, Smart exploratory test.

For more details, see [Introduction: What is Hydra Lab?](https://github.com/microsoft/HydraLab/wiki)

<span id="get-started"></span>
## Get Started

Please visit our **[GitHub Project Wiki](https://github.com/microsoft/HydraLab/wiki)** to understand the dev environment setup procedure: [Contribution Guideline](CONTRIBUTING.md).

**Supported environments for Hydra Lab agent**: Windows, Mac OSX, and Linux ([Docker](https://github.com/microsoft/HydraLab/blob/main/agent/README.md#run-agent-in-docker)).

**Supported platforms and frameworks matrix**:

|  | Appium(Java) | Espresso | 
| ---- |--------------|---- |
|Android| &#10004;     | &#10004; |
|iOS| &#10004;     | x | x |
|Windows| &#10004;     | x | 
|Web (Browser)| &#10004;     | x | 

<span id="quick-start"></span>
### Quick guide on out-of-box Uber docker image

Hydra Lab offers an out-of-box experience of docker image called Uber. You can follow the below steps and start your docker container with a center instance and an agent instance built in:

**Step 1. download and install Docker from https://www.docker.com**

**Step 2. run on your machine**

Simply choose one of the following commands to start your experience on Hydra Lab:

**1. use local storage service**

Hydra Lab Uber image uses local file system as default storage, no extra environment variable is needed:

```bash
docker run -p 9886:9886 --name=hydra-lab ghcr.io/microsoft/hydra-lab-uber:latest
```

**2. use third-party storage service**

Hydra Lab currently supports [Azure Blob Storage](https://azure.microsoft.com/en-us/products/storage/blobs/) as cloud file storage solution to persist various file types such as log files, video, app package, etc.
Any contribution to integrating other third-party storage services is welcome. (Here's the UML class diagram for this module as a reference: [storage service structure](https://github.com/microsoft/HydraLab/blob/main/docs/images/UML/storage_system_design.png).)

Some extra environment variables need to be specified in the command according to the storage service type.

If you want to use Azure Blob storage, please go to your Azure portal, open an Azure Blob storage account, and get the [connection string](https://learn.microsoft.com/en-us/azure/storage/common/storage-configure-connection-string).
Brief steps: [Login Azure](https://azure.microsoft.com/) -> [Portal](https://portal.azure.com/#home) -> [Storage Accounts](https://portal.azure.com/#view/HubsExtension/BrowseResource/resourceType/Microsoft.Storage%2FStorageAccounts) -> Create new storage account (you may disable the public access for the container) -> In the created storage account, find `Access Keys` tab -> copy `Connection string`.
![image](https://user-images.githubusercontent.com/8344245/216729523-387dc162-54d8-41dd-b136-f2e3c780b10a.png)

You may write the following content in an env file (e.g. env.properties):
```
STORAGE_TYPE=AZURE
BLOB_CONNECTION_STR=${YOUR_BLOB_CONNECTION_STR}
```

Then pass the path of the file to docker container

```bash
docker run --env-file env.properties -p 9886:9886 --name=hydra-lab ghcr.io/microsoft/hydra-lab-uber:latest
```

Or simply run with the env parameter -e:

```bash
docker run -e STORAGE_TYPE=AZURE -e BLOB_CONNECTION_STR=${YOUR_BLOB_CONNECTION_STR} -p 9886:9886 --name=hydra-lab ghcr.io/microsoft/hydra-lab-uber:latest
```

**Step 3. visit front-end page and view your connected devices**

> Url: http://localhost:9886/portal/index.html#/ (or your custom port).

Enjoy starting your journey of exploration!

**Step 4. Perform the test procedure with a minimal setup**

Note: For Android, Uber image only supports **Espresso/Instrumentation** test. See "User manual" section on this page for more features: [Hydra Lab Wikis](https://github.com/microsoft/HydraLab/wiki).

**1. To run a test with Uber image and local storage:**
- On the front-end page, go to `Runner` tab and select `HydraLab Client`.
- Click `Run` and change "Espresso test scope" to `Test app`, click `Next`.
- Pick an available device, click `Next` again, and click `Run` to start the test.
- When the test is finished, you can view the test result in the `Task` tab on the left navigator of the front-end page.

![Test trigger steps](docs/images/test-trigger-steps.png)

**2. To run a test with Uber image and any third-party storage service, additional steps are required before the same steps in `1.`:**
- Download [record_release.apk](https://github.com/microsoft/HydraLab/tree/main/common/src/main/resources/record_release.apk) and [record_androidTest.apk](https://github.com/microsoft/HydraLab/tree/main/common/src/main/resources/record_androidTest.apk) from GitHub.
- On the front-end page, go to `Runner` tab and click `Upload`.
- Select the downloaded app `record_release.apk` for `APK/IPA FILE` and test app `record_androidTest.apk` for `TEST APK/JAR/JSON FILE`.
- Click `Upload` and wait for it to finish.
- Follow the steps of 1. to run the test.

Notice: 
- The package record shown on the page is preloaded for LOCAL storage only. If you try to run it with a different storage type, you will get an error.
- You need to upload the package again if you want to use your own storage service instead of LOCAL storage.
- You can never use more than one storage type in the official service. The above specific situation only occurs in Uber image where the context and database are set up with LOCAL storage as the default option for new users.

![Package upload steps](docs/images/package-upload-steps.png)


### Quick guide on build and run

You can also run the center java Spring Boot service (a runnable Jar) separately with the following commands:

> The build and run process will require JDK11 | NPM | Android SDK platform-tools in position.

**Step 1. build and run Hydra Lab center service.**

```bash
# In project root, switch to react folder to build the Web front.
cd react
npm ci
npm run pub
# Get back to the project root, and build the center runnable Jar. 
cd ..
# For the gradlew command, if you are on Windows please replace it with `./gradlew` or `./gradlew.bat`
gradlew :center:bootJar
# Run it, and then visit http://localhost:9886/portal/index.html#/
java -jar center/build/libs/center.jar
# Then visit http://localhost:9886/portal/index.html#/auth to generate a new agent ID and agent secret.
```

> If you encounter the error: `Error: error:0308010C:digital envelope routines::unsupported`, set the System Variable `NODE_OPTIONS` as `--openssl-legacy-provider` and then restart the terminal.

**Step 2. build and run Hydra Lab agent service.**

```bash
# In project root
cd android_client
# Build the Android client apk
./gradlew assembleDebug
cp app/build/outputs/apk/debug/app-debug.apk ../common/src/main/resources/record_release.apk
# If you don't have the SDK for Android ,you can download the prebuilt APK in https://github.com/microsoft/HydraLab/releases
# Back to project root
cd .. 
# In project root, copy the sample config file and update the:
# YOUR_AGENT_NAME, YOUR_REGISTERED_AGENT_ID and YOUR_REGISTERED_AGENT_SECRET.
cp agent/application-sample.yml application.yml
# Then build agent jar and run it
gradlew :agent:bootJar
java -jar agent/build/libs/agent.jar
```

**Step 3. visit http://localhost:9886/portal/index.html#/ and view your connected devices**

**Technical design overview:**

![Tech Architecture](docs/images/technical_architecture.png)

### More integration guidelines:

- [Trigger a test task run in the Hydra Lab test service](https://github.com/microsoft/HydraLab/wiki/Trigger-a-test-task-run-in-the-Hydra-Lab-test-service)
- [Deploy a test agent service](https://github.com/microsoft/HydraLab/wiki/Deploy-a-test-agent-service)
- [Create an Appium UI Test Automation Project](https://github.com/microsoft/HydraLab/wiki/Create-an-Appium-UI-Test-Automation-Project)

> Note: If you are a Microsoft FTE and want to onboard to the internal Hydra Lab testing service, please visit [our SharePoint site](https://microsoftapc.sharepoint.com/teams/MMXDocument/SitePages/Hydra-Lab-test-automation-service-onboarding-guideline.aspx) to learn more about the internal service instance.

<span id="who-use-it"></span>
## Who are using Hydra Lab?

It's already powering the UI test automation of the following Microsoft products:
- Microsoft Phone Link (Windows UWP app) and Link to Windows (Android app)
- Microsoft Launcher (Android)
- Microsoft Outlook/Edge (Android/iOS)
- Microsoft Fluent UI Android/Yammer Android

<span id="contribute"></span>
## Contribute

Your contribution to Hydra Lab will make a difference for the entire test automation ecosystem. Please refer to **[CONTRIBUTING.md](CONTRIBUTING.md)** for contribution instructions.

### Contributor Hero Wall:

<a href="https://github.com/Microsoft/hydralab/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=Microsoft/hydralab" />
</a>

<span id="contact"></span>
## Contact Us

Feel free to dive in! If you have questions about Hydra Lab, or you would like to reach out to us about an issue you're having, you can reach us as follows:
- [Open an issue](https://github.com/microsoft/HydraLab/issues/new) or submit PRs.
- Email us: [hydra_lab_support@microsoft.com](mailto:hydra_lab_support@microsoft.com).

<span id="links"></span>
## Links

- [Hydra Lab: the next generation cross-platform software testing infrastructure open-sourced by Microsoft](https://medium.com/@nathanbu/we-open-sourced-a-new-test-framework-on-github-in-microsoft-fd31f8861d1e)
- [Release Notes](https://github.com/microsoft/HydraLab/releases), [Hydra Lab on Up For Grabs](https://up-for-grabs.net/#/filters?names=438)
- [Appium: Cross-platform automation framework for all kinds of your apps built on top of W3C WebDriver protocol.](https://github.com/appium/appium)
- [Google Android Tools Ddmlib: A ddmlib jar that provides APIs for talking with Dalvik VM.](https://android.googlesource.com/platform/tools/base/+/master/ddmlib/)

<span id="ms-give"></span>
## Microsoft Give Sponsors

Thank you for your contribution to [Microsoft employee giving program](https://aka.ms/msgive) in the name of Hydra Lab:

[@Germey(崔庆才)](https://github.com/Germey), [@SpongeOnline(王创)](https://github.com/SpongeOnline), [@ellie-mac(陈佳佩)](https://github.com/ellie-mac), [@Yawn(刘俊钦)](https://github.com/Aqinqin48), [@White(刘子凡)](https://github.com/jkfhklh), [@597(姜志鹏)](https://github.com/JZP1996), [@HCG(尹照宇)](https://github.com/mahoshojoHCG)

![Microsoft Give](docs/images/Give_WebBanner.png)

<span id="license-trademarks"></span>
## License & Trademarks

The entire codebase is under [MIT license](https://github.com/microsoft/HydraLab/blob/main/LICENSE).

This project may contain trademarks or logos for projects, products, or services. Authorized use of Microsoft trademarks or logos is subject to and must follow [Microsoft’s Trademark & Brand Guidelines](https://www.microsoft.com/en-us/legal/intellectualproperty/trademarks/usage/general). Use of Microsoft trademarks or logos in modified versions of this project must not cause confusion or imply Microsoft sponsorship. Any use of third-party trademarks or logos are subject to those third-party’s policies.

