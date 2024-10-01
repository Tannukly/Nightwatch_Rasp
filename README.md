# Challenge_Rasp

![logo da empresa](https://i.pinimg.com/736x/14/de/e6/14dee646c4b36cbbbd101d4e97612101.jpg)

## 🇧🇷 Visão geral
Este repositório foi criado com o propósito de desenvolver uma solução para garantir a segurança e a integridade de um APK. A solução proposta é um RASP (Runtime Application Self-Protection), uma tecnologia de segurança cibernética que protege aplicativos em tempo real, detectando e bloqueando ameaças durante a execução.

## Observações 
Nosso RASP foi criado com o objetivo de proteger o aplicativo InsecureBankv2 desenvolvido por Dinesh Shetty. Você pode fazer o download da pasta do aplicativo no seguinte link do GitHub: https://github.com/dineshshetty/Android-InsecureBankv2/

### 🛠️Ferramentas do RASP:
#### 1. Detecção de Root:
O código utiliza várias abordagens para detectar se o dispositivo no qual o aplicativo está sendo executado foi "rootado" (ganhou acesso a permissões administrativas):

* Verifica a existência de arquivos e binários comumente associados ao root, como Superuser.apk e su.
* Executa comandos do sistema (which su e which busybox) para verificar a presença dessas ferramentas.
* Checa se o dispositivo foi construído com as "test-keys", que são usadas em versões de desenvolvimento do Android.
#### 2. Detecção de adulteração do aplicativo:

* Verifica a assinatura do aplicativo para identificar possíveis modificações no arquivo APK.
* Calcula o hash do arquivo classes.dex (parte do APK que contém o código compilado) e o compara com um hash esperado para detectar alterações não autorizadas.
#### 3. Detecção de execução em emulador (VM):

* Verifica se o dispositivo tem características de emulador, como Build.FINGERPRINT, MODEL, HARDWARE, PRODUCT e BOARD.
* Confere o número de núcleos da CPU disponíveis; dispositivos emulados geralmente têm um número muito baixo de núcleos.
* Verifica o comportamento da rede, como endereços IP que são comumente usados por emuladores (ex: 10.0.2.* ou 127.0.0.1).
#### 4.Detecção de depurador:

* Verifica se um depurador está conectado ao processo do aplicativo usando as funções Debug.isDebuggerConnected() e Debug.waitingForDebugger().
* Introduz um pequeno delay (2 segundos) quando um depurador é detectado, possivelmente para frustrar tentativas de depuração.


## Aqui você vai encontrar os seguintes arquivos e pastas:
* Rasp InsecureBankv2
* insecureBankv2 - Rasp.apk
* rasp.java
* Manual_para_compilar.pdf

## 📁Observação dos arquivos e pastas

### Rasp InsecureBankv2:
Aqui você vai encontrar a aplicação InsecureBankv2 com a solução RASP já implementada. Esta pasta serve para que você faça o download com o objetivo de estudar, modificar ou melhorar o RASP, utilizando o Android Studio para compilar a pasta após realizar suas modificações.

### InsecureBankv2 - Rasp.apk:
Este é o APK do InsecureBankv2 com o nosso RASP já implementado, para que você possa verificar as funcionalidades das ferramentas do RASP ou testar vulnerabilidades que nossa solução ainda possa não mitigar.

### Rasp.java:
Este é o código do nosso RASP, feito inteiramente em Java. Com ele, você pode estudar e analisar como todas as ferramentas de detecção foram criadas. Assim, ele servirá como um código base para você começar a aprimorar.

### Manual_para_compilar.pdf:
O arquivo Manual_para_compilar.pdf é para você que não sabe compilar e precisa de orientações para utilizar nosso RASP ou se você pretende melhorá-lo.

## 🇺🇸 Overview
This repository was created with the purpose of developing a solution to ensure the security and integrity of an APK. The proposed solution is a RASP (Runtime Application Self-Protection), a cybersecurity technology that protects applications in real time by detecting and blocking threats during execution.

## Notes:
Our RASP was created with the purpose of protecting the InsecureBankv2 application, developed by Dinesh Shetty. You can download the application folder from the following GitHub link: https://github.com/dineshshetty/Android-InsecureBankv2/

### 🛠️RASP Tools:
#### 1.Root Detection:
The code uses several approaches to detect if the device on which the application is running has been rooted (gained administrative permissions):

*Checks for the existence of files and binaries commonly associated with root, such as Superuser.apk and su.
*Executes system commands (which su and which busybox) to check for the presence of these tools.
*Checks if the device was built with "test-keys," which are used in development versions of Android.
#### 2. Application Tampering Detection:

* Verifies the application's signature to identify possible modifications to the APK file.
* Calculates the hash of the classes.dex file (part of the APK containing compiled code) and compares it with an expected hash to detect unauthorized changes.
#### 3. Emulator (VM) Detection:

* Checks if the device has emulator characteristics, such as Build.FINGERPRINT, MODEL, HARDWARE, PRODUCT, and BOARD.
* Checks the number of CPU cores available; emulated devices usually have a very low number of cores.
* Verifies network behavior, such as IP addresses commonly used by emulators (e.g., 10.0.2.* or 127.0.0.1).
#### 4. Debugger Detection:

* Checks if a debugger is connected to the application process using the functions Debug.isDebuggerConnected() and Debug.waitingForDebugger().
* Introduces a small delay (2 seconds) when a debugger is detected, possibly to frustrate debugging attempts.

## Here you will find the following files and folders:
* Rasp InsecureBankv2
* insecureBankv2 - Rasp.apk
* rasp.java
* Manual_para_compilar.pdf

## 📁Notes on the files and folders:

### Rasp InsecureBankv2:
Here you will find the InsecureBankv2 application with the RASP solution already implemented. This folder is intended for you to download it for the purpose of studying, modifying, or improving the RASP, using Android Studio to compile the folder after making your modifications.

### insecureBankv2 - Rasp.apk:
This is the APK of InsecureBankv2 with our RASP already implemented, allowing you to check the functionalities of the RASP tools or test for vulnerabilities that our solution might not yet mitigate.

### rasp.java: 
This is the code for our RASP, written entirely in Java. With it, you can study and analyze how all the detection tools were created. It will serve as a base code for you to start improving.

### Manual_para_compilar.pdf:
The file Manual_para_compilar.pdf is for those who do not know how to compile and need guidance to use our RASP or if you intend to improve it.
