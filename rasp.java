package com.android.insecurebankv2;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.util.Enumeration;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_main);

        // Chama a função de verificação de segurança
        if (isSecurityCompromised(this)) {
            // Fecha o aplicativo em caso de anomalia detectada
            Log.e("SecurityCheck", "Anomalia detectada! Fechando o aplicativo.");
            Toast.makeText(this, "Segurança comprometida. Aplicativo será encerrado.", Toast.LENGTH_LONG).show();
            finish();  // Fecha a aplicação
        }
    }

    // Função que verifica se há qualquer anomalia de segurança
    private boolean isSecurityCompromised(Context context) {
        if (SecurityUtils.isDeviceRooted()) {
            Log.e("SecurityCheck", "Root detectado!");
            return true;
        }

        if (SecurityUtils.isAppTampered(context)) {
            Log.e("SecurityCheck", "Adulteração de aplicativo detectada!");
            return true;
        }

        if (SecurityUtils.isRunningOnEmulator(context)) {
            Log.e("SecurityCheck", "Execução em Máquina Virtual (VM) detectada!");
            return true;
        }

        if (SecurityUtils.isDebuggerAttached()) {
            Log.e("SecurityCheck", "Depurador detectado!");
            return true;
        }

        return false;
    }
}

class SecurityUtils {

    // 1. Detecção de Root
    public static boolean isDeviceRooted() {
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3() || checkRootMethod4();
    }

    private static boolean checkRootMethod1() {
        String[] paths = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su"};
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }

    private static boolean checkRootMethod2() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) return true;
        } catch (Throwable t) {
            return false;
        }
        return false;
    }

    private static boolean checkRootMethod3() {
        return Build.TAGS != null && Build.TAGS.contains("test-keys");
    }

    private static boolean checkRootMethod4() {
        // Verifica se o BusyBox está presente
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "busybox"});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) return true;
        } catch (Throwable t) {
            return false;
        }
        return false;
    }

    // Verificação de permissões do sistema
    private static boolean checkSystemFiles() {
        File systemDir = new File("/system");
        File dataDir = new File("/data");
        return !systemDir.canWrite() || !dataDir.canWrite();
    }

    // 2. Detecção de adulteração do aplicativo
    public static boolean isAppTampered(Context context) {
        if (!verifySignature(context)) {
            return true;
        }

        // Verifica o hash do arquivo classes.dex
        try {
            File file = new File(context.getPackageCodePath());
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
            fis.close();
            byte[] hash = digest.digest();

            // Comparar com o hash esperado
            String expectedHash = "hash_calculado_esperado";
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            if (!sb.toString().equals(expectedHash)) {
                return true;  // Aplicativo foi adulterado
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }

        return false;
    }

    private static boolean verifySignature(Context context) {
        // Verificação básica de assinatura, poderia ser aprimorada com APIs nativas.
        return true;  // Exemplo simplificado
    }

    // 3. Detecção de Máquina Virtual (VM)
    public static boolean isRunningOnEmulator(Context context) {
        if (Build.FINGERPRINT.startsWith("generic")
                || Build.MODEL.contains("Emulator")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.HARDWARE.equals("goldfish")
                || Build.HARDWARE.equals("ranchu")
                || Build.PRODUCT.equals("sdk")
                || Build.BOARD.toLowerCase().contains("nox")) {
            return true;
        }

        // Verifica o número de núcleos da CPU
        if (Runtime.getRuntime().availableProcessors() <= 1) {
            return true;  // Provavelmente é um emulador
        }

        // Verifica comportamento de rede
        String ipAddress = getIPAddress();
        if (ipAddress.startsWith("10.0.2.") || ipAddress.equals("127.0.0.1")) {
            return true;  // Provavelmente emulado
        }

        return false;
    }

    private static String getIPAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "127.0.0.1";
    }

    // 4. Detecção de depurador
    public static boolean isDebuggerAttached() {
        if (Debug.isDebuggerConnected() || Debug.waitingForDebugger()) {
            try {
                Thread.sleep(2000);  // Introduz delay para frustração do invasor
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    // 5. Ofuscação de Código (exemplo configurado no Proguard/R8)
    /*
    # No arquivo proguard-rules.pro
    -keep class com.suaempresa.security.** { *; }
    -keepclassmembers class * {
        @androidx.annotation.Keep *;
    }

    # Ofuscar o restante do código
    -obfuscate
    */
}
