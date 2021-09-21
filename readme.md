# Tricycle Code

This is the code that is used on the Tricycle robot created from the [Studica FTC Starter Kit](https://www.studica.ca/en/ftc-starter-kit-2021).

This code was created using the FTC 7.0 SDK in Android Studio.

## Android Studio Setup

To install Android Studio follow the steps here https://github.com/FIRST-Tech-Challenge/FtcRobotController/wiki/Installing-Android-Studio

After installing Android Studio follow these steps to download and open the 7.0 SDK. https://github.com/FIRST-Tech-Challenge/FtcRobotController/wiki/Downloading-the-Android-Studio-Project-Folder

OpenCV needs to be installed as well the 7 step instructions [here](https://github.com/OpenFTC/EasyOpenCV#installation-instructions-android-studio) are easy to follow for this.

The navX library also needs to be installed. The instructions on the KauaiLabs website are a bit dated and no longer work. But following below will allow you to add the library.

1. Install the navX tools [here](https://www.kauailabs.com/public_files/navx-micro/navx-micro.zip)

2. Under Gradle Scripts find `build.dependencies.gradle` and open it

3. Look for the flatDir{} section and add `dirs 'libs', 'C:\\Users\\james\\navx-micro\\android\\libs'` but with your correct directory. AKA replace james with your username.

4. In the same file is the dependencies{} section. Add `compile (name:'navx_ftc-release', ext:'aar')` at the bottom.

5. Hit the sync now on the Gradle message or hit the elephant icon in the top right to sync the Gradle files.

## Adding the code

1. Clone or download this repo to your computer.

2. Place all the files here into `TeamCode/src/main/java/org/firstinpires/ftc/teamcode/`.

3. Run a build by clicking on the green hammer in the top middle of the screen

## Modifying the code

- In TricycleOp, there is `Utilities.Alliance alliance = Utilities.Alliance.BLUE;` change the `BLUE` to `RED` if you are on the RED alliance. This will configure the code to change directions correctly.

- In DriveTrain, the PID coefficients may be calibrated for the turning functions.

- In DriveTrain, the motor flags might need to be changed from `REVERSE` to `FORWARD` depending on your motor or how you wired it.

- In Elevator, the lift direction might need to be changed from `REVERSE` to `FORWARD` depending on your motor or how you wired it.

## Driving the Robot

- When enabled the left joystick Y axis is for driving forwards or backwards

- The left joystick X axis handles the left and right movement

- Moving the left joystick in the X and Y direction will mesh the movements

- Moving the right joystick in the X axis will spin the robot left or right

- The Right Bumper will strafe the robot to the right

- The Left Bumper will strafe the robot to the left

- Square (PS4) or X (Logitech) will turn the robot so it can enter the first gate

- Triangle (PS4) or Y (Logitech) will turn the robot so it can enter the gate for the shared object

- X (PS4) or A (Logitech) will spin the carousel. If the alliance was set correctly it will spin the correct direction

- Dpad UP will move the elevator up

- Dpad DOWN will move the elevator down