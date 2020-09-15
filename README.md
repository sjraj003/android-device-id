# android-device-id
#

#

#

#

#

#

#

# Device ID Generation

#

#

#

# Table of Contents

_**[1.](#_Toc49706933)**__ **Existing logic to Generate Device-ID 3** _

**[1.1](#_Toc49706934)****Debug build 3**

**[1.2](#_Toc49706935)****Simulator 3**

**[1.3](#_Toc49706936)****Unique device ID through the android Telephony manager 3**

**[1.4](#_Toc49706937)****Secure android ID through the android Secure Setting 3**

**[1.5](#_Toc49706938)****Mac Address through the android Wifi manager 3**

**[1.6](#_Toc49706939)****Unique Random Number 3**


1.
# Existing logic to Generate Device-ID

  1.
## Debug build

Hard coded device ID: &quot;867686020986012&quot;

  1.
## Simulator

Hard coded device ID: &quot;1234&quot;

  1.
## Unique device ID through the android Telephony manager

(Issue in the Android10 API Level 29)

- TelephonyManager.getDeviceId() - Returns the unique device ID.
  - GSM Devices – IMEI
  - CDMA Devices – MEID or ESN
  - Return null if the device ID is not available.
- Requires Permission: android.manifest.permission.READ\_PHONE\_STATE.
- Deprecated from the android version: Oreo-8.0.0 (API Level 26).
- To access this API from the android version 8.0.0, the calling application should have the android.Manifest.permission.READ\_PRIVILEGED\_PHONE\_STATE permission.
- And this permission is a privileged permission that can only be granted to apps preloaded on the device.

  1.
## Secure android ID through the android Secure Setting

- Settings.Secure.ANDROID\_ID

- On Android 8.0 (API level 26) and higher versions of the platform, a 64-bit number (expressed as a hexadecimal string), unique to each combination of the app-signing key, user, and device.
- Values of ANDROID\_ID are scoped by signing key and user.
- The value may change if a factory reset is performed on the device or if an APK signing key changes.
- For apps that were installed prior to updating the device to a version of Android 8.0 (API level 26) or higher, the value of ANDROID\_ID changes if the app is uninstalled and then reinstalled after the OTA.

  1.
## Mac Address through the android Wifi manager

- WifiInfo.getMacAddress()

- Requires Permission: **android.permission.LOCAL\_MAC\_ADDRESS**

  1.
## Unique Random Number

- UUID.randomUUID()
- Cryptographically strong pseudo random number generator.

##

  1.
## Firebase instance ID

- Firebase Instance ID provides a unique identifier for each app instance and a mechanism to authenticate and authorize actions (example: sending FCM messages).
- Instance ID is stable except when:
  - App deletes Instance ID
  - App is restored on a new device
  - User uninstalls/reinstall the app
  - User clears app data
- Once an Instance ID is generated, the library periodically sends information about the application and the device where it&#39;s running to the Firebase backend. To stop this, see deleteInstanceId().

| **S.No** | **Type** | **Life span of the generated ID / Limitation** | **Device reset (Factory data reset) survival Yes/ No** | **App reset (Uninstall &amp; Re-Install) survival Yes/ No** | **App Upgrade survival Yes/ No** |
| --- | --- | --- | --- | --- | --- |
| 1 | DeviceID - Through Telephony manager | This is the serial of the device, which should persist even a factory reset. | Yes | Yes | Yes |
| 2 | AndroidID - Through the android Secure Setting | This will be set at the first boot (either with a brand new device, or after a factory reset). As implicated, it does not survive a factory-reset | No | Yes | Yes |
| 3 | Mac Address - Through Wifi manager | MAC addresses are globally unique, not user-resettable, and survive factory resets. For these reasons, it&#39;s generally not recommended to use MAC address for any form of user identification. Need Wifi Connection | Yes | Yes | Yes |
| 4 | Unique Random Number | UUID.randomUUID() method generates an unique identifier for a specific installation. You have just to store that value and your user will be identified at the next launch of your application. You can also try to associate this solution with Android Backup service to keep the information available for the user even if he installs your application on an other device | No | No | Yes |
| 5 | Firebase Instance ID | Instance ID is stable except when:
 \* App deletes Instance ID
 \* App is restored on a new device
 \* User uninstalls/reinstall the app
 \* User clears app data | No | No | Yes |


