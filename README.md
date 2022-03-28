# Unis2 - text/file crypting/coding tool
This JavaFX-based desktop tool allows you to encrypt & decrypt files with different algorithms.
Also it includes data encoding & hashing.
Almost all methods from here can be found as online services, but why not to collect them to local desktop program?
# Where it can be used?
Initially it was designed to privately communicate with people in non-protected chats (public chats with other people).
You can exchange keys with a person in a protected channel (f.ex. in real life) and use this key both to encrypt & decrypt messages, or use daily keygen with kg-key to generate new synchronized keys every day.
If other person intercepts your message, without a key he can't decrypt it and see the contents.
You can exchange keys with your friend and talk safely in any public channel by crypting messages in Unis2.
# Usage
Block in the top of app allow to generate unique keys based on current date (timezone +0) & keygen key.

Below you can see cipher chooser. Hover on it to see cipher details, click to change.

Fill plaintext area with data (or turn on file mode and select a file) and click "ENCRYPT"

Result will appear in area below.

The same for decrypting.

**When decrypting .uniscrypt files, decrypting result will be saved in file with name without .uniscrypt with overwriting existing file**

**For example, you encrypted a.txt -> a.txt.uniscrypt and decrypted a.txt.uniscrypt. Result will be saved to a.txt & overwrite it.**
# Building
This project uses Maven to build executable jars.
Clone the project and run
```
mvn clean package
```
to build jars in target.

Shaded "fat" jar can be used with Java 11+, and original lightweight jar is for versions below 11.

_As I seen before, JavaFX fat-jar apps doesn't launch properly on Linux with Java 11+, so try to use Java 8._
