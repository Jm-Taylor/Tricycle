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

# Autonomous 

The Autonomous code uses OpenCV to easily determine which of the three barcodes has a duck on it. 

## Calibrating the Pipeline

The bulk of the vision is done inside a pipeline. The pipeline is essentially a vision script that allows the processing of an image and then returning that image when its done. 

There are only a few sections that need to be adjusted for ROI boxes and a threshold value.

```java
   static final Point BARCODE_1_TOP_LEFT = new Point(10, 275);
   static final Point BARCODE_2_TOP_LEFT = new Point(300, 275);
   static final Point BARCODE_3_TOP_LEFT = new Point(539, 250);

   static final int BARCODE_WIDTH = 100;
   static final int BARCODE_HEIGHT = 75;

   final int OBJECT_THERE_THRESHOLD = 125;
```
The `BARCODE_X_TOP_LEFT` points refers to the `(X, Y)` point of the top left of a rectangle. This is where you set the location of your ROI boxes. 

The X and Y values are the pixel locations on the image. In the example the code is using a `640 x 480` image taken from the camera. 

(0,0) ------------- (640,0)
/                       /
/                       /
/                       /
/                       / 
/                       /
/                       /
/                       /
(0,480)------------ (640,480)

The locations of each pixel is demonstrated above with the origin (0,0) at the top left. 

Next value to calibrate would be the width and the height of the ROI rectangle for each barcode. The example creates a `100 x 75` pixel ROI rectangle, however, you may wish to change this based on the location of your camera or to improve accuracy. The smaller the ROI rectangle is the more accurate the averaging calculation done in the code is. 

The last value to be calibrated is the `OBJECT_THERE_THRESHOLD`. This can only be done through testing on the court. It is very easy to do. 

1. Put the Robot on the Court with no ducks placed. 

2. Run the code and see what the three outputs are. (They should be pretty similar) In my testing I found the values to be around `119` when there was no duck.

3. Record the values with no Duck

4. Place a duck on the barcode and run the code again. The numbers should now go upwards. In my testing I found the values were above `130`.

5. Record the values with the Duck.

6. Now to get the value to put for the `OBJECT_THERE_THRESHOLD`, we will take a number inbetween the two values we just observed. For example `((130 - 119) / 2) + 119 = 124.5` So our `OBJECT_THERE_THRESHOLD` should be `124.5` but we will round up to `125`.

## Results

For the duck on the left

<img src='img/Camera Pos 1.png' width='50%'><img src='img/Result 1.png' width='50%'><br/>

For the middle duck

<img src='img/Camera Pos 2.png' width='50%'><img src='img/Result 2.png' width='50%'><br/>

For the duck on the right

<img src='img/Camera Pos 3.png' width='50%'><img src='img/Result 3.png' width='50%'><br/>

