package com.lctapp.lct.Classes.security

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment

class Biometric(private val activity: AppCompatActivity){

  fun showBioDialog() {
        val executor = ContextCompat.getMainExecutor(activity)
        val biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(activity,errString.toString(), Toast.LENGTH_LONG).show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)

                    //Do your things

                    Log.e("here",result.toString())

                    //delay until the fragment has resumed
                    Toast.makeText(activity,"Success", Toast.LENGTH_LONG).show()
                }

//                override fun onAuthenticationFailed() {
//                    super.onAuthenticationFailed()
//
//                }
            })
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Scan fingerprint or use credentials")
            .setSubtitle("Confirm your identity using your biometric or credentials")
            .setDeviceCredentialAllowed(true)
            .build()
        biometricPrompt.authenticate(promptInfo)


    }

}