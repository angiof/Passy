package passy.prog.root

import android.content.Context
import java.io.File

class DeviceUtils {
    fun isDeviceRooted(context: Context?): Boolean {
        return isrooted1() || isrooted2()
    }

    private fun isrooted1(): Boolean {
        val file = File("/system/app/Superuser.apk")
        return file.exists()
    }

    // try executing commands
    private fun isrooted2(): Boolean {
        return (canExecuteCommand("/system/xbin/which su")
                || canExecuteCommand("/system/bin/which su")
                || canExecuteCommand("which su"))
    }

    private fun canExecuteCommand(command: String): Boolean {
        val executedSuccesfully: Boolean = try {
            Runtime.getRuntime().exec(command)
            true
        } catch (e: Exception) {
            false
        }
        return executedSuccesfully
    }

    fun isRootAvailable(): Boolean {
        for (pathDir in System.getenv("PATH")?.split(":".toRegex())?.dropLastWhile { it.isEmpty() }
        !!.toTypedArray()) {
            if (File(pathDir, "su").exists()) {
                return true
            }
        }
        return false
    }
}