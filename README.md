# NFCAndroidTests

All Tests are Android Studio projects.

The first tests are quite ugly as I was learning how Java and Android works.

NFC-Test4 and NFC-Test5 are the important tries.

## Test4 
It is created by going through http://developer.android.com/guide/topics/connectivity/nfc/nfc.html and coding the app based on that. The intention is to register the app such that when the NFC tag is scanned the Activity chooser pops-up and displays my app.

Tried the following in AndroidManifest.xml:

### Case 1:
```xml
<intent-filter>
   < action android:name="android.nfc.action.TAG_DISCOVERED"/>
</intent-filter>
```

#### Outcome: The sound played when the NFC tag is swiped has changed (it’s high pitched, more cheerful) from previous code tests (i.e. test3). Though the app is still not started automatically. By adding inside the <intent-filter> to get this: 
```xml
<intent-filter>
  <action android:name="android.nfc.action.TAG_DISCOVERED"/>
  <category android:name="android.intent.category.DEFAULT"/>
</intent-filter>
```
the app starts when the NfcA tag is swiped (the cheerful sound is also played).
If I swipe the K1 the low pitch sound is played and the app does not start.

### Case 2:
```xml
<intent-filter>
  <action android:name="android.nfc.action.NDEF_DISCOVERED" />
  <category android:name="android.intent.category.DEFAULT" />
  <data android:mimeType="text/plain" />
</intent-filter>
```
#### Outcome: The sound played when the NFC tag is swiped is low pitched the same as in previous tests (i.e. test 3)

### Case 3:
```xml
<intent-filter>
  <action android:name="android.nfc.action.TECH_DISCOVERED" />
</intent-filter>

<meta-data android:name="android.nfc.action.TECH_DISCOVERED"
android:resource="@xml/nfc_tech_filter"/>
```
and in xml/nfc_tech_filter:
```xml
<resources xmlns:xliff="urn:oasis:names:tc:xliff:document:1.2">
<tech-list>
<tech>android.nfc.tech.NfcA</tech>
</tech-list>
</resources>
```
#### Outcome: When the NfcA tag (white CC looking NFC) is swiped the app starts. If I change NfcA to IsoDep (ISO-14443-4) and swipe K1 the app does not start and the low pitch sound is played.


## NFC-Test5 
This is an in app NFC swipe test. That is the app needs to be started before the NFC swipe occurs and 
the intent is picked up by the  app.

Test 5 attempts to implement (http://tapintonfc.blogspot.de/2012/07/the-above-footage-from-our-nfc-workshop.html). This is a project that uses the AS3953 NFC IC we use on the K1.

Found this comment on stackoverflow from user Michael Roland (creator of NFC TagInfo):
The different sound (I assume this one (or possibly only this one) instead of the one that you would hear upon success) is typically played when the device could not establish communication with a detected tag.
        
This could have various reasons, for instance, that the tag was not properly placed on the device's NFC antenna, or that the tag's antenna does not sufficiently couple with the device's NFC antenna and that, consequently, the tag did not receive sufficient power to wake up on time or that the Nexus 7 did not detect the tag's response (due to too little modulation depth).

Sometimes, such a problem with NFC tag detection results in crashes of the NFC system service (that's what happens in your case and what you recover from by re-enabling NFC in the Settings app).

Anyways, as the tag was not properly detected by the Android system (and as the NFC service crashed), no NFC discovery intents will be passed to your app.

