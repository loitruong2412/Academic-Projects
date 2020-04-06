Team Mates: Loi Truong and Tiffany Chen

== UPDATES ==

04-10-2019: Bug fixed in the ImageModelImpl constructor for a deep copy of the 3D array that 
represents the image and changed needed methods and fields from private to protected. Extended 
ImageModel Interface and ImageModelImpl to add additional features, dither and mosaic.
Refactor into packages for cleaner organization. Implemented the controller to handle the model. 
Allows for parsing of input. Tested separately as a component with a MockModel.

04-02-2019: Model for the application has been completed and a separate ImageDriver along with some 
junit tests are used to test the model. 
    
== INTRODUCTION: ==

Our goal is to create a image processing application. We plan to implement a MVC design pattern 
with this application. It is used for creating new images from scratch or processing images with 
basic filter and color transformation techniques. The program currently is planned to support the
following features:
1. Apply filter on an image with a kernel, represented by a 2D array of floats, specified by the 
users or the user can simple use the built-in blur or sharpen feature that implements a predefined 
kernel.
2. Apply color transformation on an image with a matrix, represented by a 2D array of doubles, 
specified by the users or from the built-in greyscale and sepia-toned feature.
3. Generates flags of countries with specified country, width and height (in pixels). The currently 
supported list of countries is: France, Greece, Switzerland.
4. Generates an 8x8 black and white checkerboard with the size of the squares specified by users.
5. Generates a rainbow with direction and dimensions specified by users. Currently supports 
Vertical and Horizontal rainbows.
6. Apply dither on an image which coverts an image into black and white dots.
7. Apply mosaic on an image which converts an image into a mosaic-like photo with a 
"stained glass window" effect. A number of seeds determines how abstract the image becomes, the 
closer the number of seeds to the number of pixels in image the more like the original image it 
will be.

Currently, completed is the model and controller that handles the model for the program. This model 
only takes in data of image files from the controller or to generate images take in dimensions. 
All the above mentioned features are supported by our model. The controller parses the input and 
ensures that the correct data and commands are executed. The controller currently also outputs 
messages for any user errors.

== HOW TO USE ==

When users initialize this program, they have the option to load image or create one into the 
program. Images read in by the program are stored using a 3D array of integers. Our model processes
the image and saves it as the 3D array inside the program. Our model handles a few features of our 
program by processing the image changing the 3D array and returning a new 3D array after the 
change. To get the processed image from the model, users can call method getData and receive the 
image represented by a 3D array of integers. The controller.ImageUtil class is provided for the 
users to write the image out to a file of their choice.

If the users choose to use this program to create images with the existing features, they don't have
to load in any image when initializing the program.

| Controller |  (Added 4.10.2019)

To use the controller, you can use the provided jar file and provide a script in a text file. To 
use the controller you must start with the load or create command. If you do not load an image or
create an image, you have no image to work with.
To use the jar file:

       java -jar hw9-Tiffany.jar *(text file script)*

Supported commands and required attributes:
    
    Commands                                       Process
    q or quit                                      -- To exit or quit
    load *(filename with the correct path)*        -- To load in an image and create a model object
    save *(filename)*                              -- Saves the photo into the current directory
    restore                                        -- Covert back to original loaded image. Cannot 
                                                      revert back to created image unless loaded
                                                      into model.
    blur                                           -- Blurs the image
    sharpen                                        -- Sharpens the image
    sepia                                          -- Color transforms it to sepia
    greyscale                                      -- Color transforms it to greyscale
    dither                                         -- Transforms the image to dithered (black and
                                                       white dots)
    mosaic *(number of seeds)*                     -- Transforms the image into a mosaic version
    
    create *(supported image and its attributes)*  -- Creates new image with given attributes
       * checkerboard (size of square)*            -- 8x8 Checkboard with the pixel size of the sqs
       * rainbow (height) (width) (vertical or horizontal)*
       * flag (height) (width) (France, Greece, or Switzerland)*
        
    Attributes below have rows for the following is seperated by a semicolon. While each column is
    seperated by a colon. Kernel takes in fractions while, matrix takes in decimals.
    
    filter *(kernel)*                              -- Filter by the given kernel (advance users)             
        Example: filter 1/9,1/9,1/9;1/9,1/9,1/9;1/9,1/9,1/9
    transform *(matrix)*                           -- Color transform using a matrix (advance users)
        Example: transform 0.2126,0.7152,0.0722;0.2126,0.7152,0.0722;0.2126,0.7152,0.0722
    

| Model | 

**Update 4.10.2019:**

**To add features, the original Model interface and Impl was extended. Use 
ImageModelPlus Object for interface and the object to be created with class ImageModelImplPlus. 
Controller can now be used rather than loading and generating images like the examples below. 
Testing of additional model features was done in ImageDriverTester before the controller was 
created also exceptions were checked in ImageModelImplPlusTest.**

To use the model ImageModel Object must be created with the constructor of Class ImageModelImpl.
The ImageModelImpl constructor takes in a 3D array of a data representation of an image if you want 
to edit an image. Alternatively, the constructor can take in nothing to generate an image. You 
can apply multiple processes to a single image.

Note to get a 3D array of an image the controller can use controller.ImageUtil.readImage. Currently,
we also used controller.ImageUtil.writeImage to test out that our code would generate correct 
images. More examples can be found in our ImageDriver which acts to test our code.

Examples:
To edit a photo:
    
    int[][][] imageDataFox = controller.ImageUtil.readImage("fox.jpg");
    model.ImageModel image = new model.ImageModelImpl(imageDataFox);
    image.sepia();

To generate a rainbow:
    
    model.ImageModel model = new model.ImageModelImpl();
    model.generateRainbow(8, 8, enums.Direction.VERTICAL);
    int[][][] vertRain = model.getData();

            
Generated rainbow and then blur and greyscale:
    
    model.ImageModel rainbowMulti = new model.ImageModelImpl();
    rainbowMulti.generateRainbow(300, 350, enums.Direction.HORIZONTAL);
    rainbowMulti.blur();
    rainbowMulti.blur();
    rainbowMulti.greyscale();
    int[][][] rainbowMultiData = rainbowMulti.getData();
    
To get the data of the processed picture to a file:
    
    int[][][] iData = image.getData();
    ImageUtil.writeImage(image.getData(), iData[1].length, iData.length, "sepiafox.png");

== DESIGN LOGIC: ==

Update 4.10.2019:

As stated below we planned to expand model with inheritance with dedication to the SOLID principals.

Our controller, utilizes a map to check if the command exists in the map. If it is, it executes 
the the function object to get the command object. This resulted in a number of command objects 
located in the commands package and a ImageCommand interface in the controller. This was decided 
on as it was cleaner than having a long unwieldy switch statement with too many cases. Future 
features can be easily implemented by creating a new command class that implements ImageCommand. 
The map is also privately created, so that the new feature can be added to map easily.

For certain commands, they are handled in a switch statement since they are more related to the 
view and have variables that require certain actions. 

We chose to exit the function and have an out of the error message rather than throw Exceptions. 
This choice made since the controller is closer to the user and exceptions will throw back a ugly
error with a stack trace. To hide this we catch the exceptions and have a message instead. The 
idea is to also prevent a program crash.

Testing of the controller, was done with a MockModel. We tested that the calls to the model were 
correct and that the output was correct for errors and exiting the process.

Update 4.02.2019:

We chose to go with a ImageModel Interface and a ImageModelImpl Class. To expand the model in 
future updates a new interface can extend the current interface while a new ModelImpl can expand 
the current one and add needed methods that will support additional features. This will allow 
extension of the components without modification.

Enums were used for generateRainbow and generateFlags for our predefined list of currently 
supported types of generated rainbows and flags. This choice was made as we see the user given 
the specific choices as they are supported. Additional instead of specific generate methods the 
user of the model can easily use one method to create flags which is more user-friendly, rather 
than needing to remember if it is generateFranceFlag or generateFrenchFlag. The method 
generateFlags can also expand in the future easily for different flags without 
modification of existing code.

For generating flags, we decided it was more user friendly to still be able to return an image 
with the specified size and the flag even when the proportions are off. The extra space will be 
white and a black border to surround the correctly outputted flag. 

Additional complex methods, such as filter() and colorTrans() is available in case we want to 
support advance features, in our image processor these more complex methods could take in certain
information from user in the view to allow them to apply a filter or a color transformation. It 
also can be used for expanding available future filters or color transformations, such as edge 
detection or a different color.


== IMAGE CITATION: ==
Following external images were used to test our code.

Unsplash's Copyright License:
All photos published on Unsplash can be used for free. You can use them for commercial and 
noncommercial purposes. You do not need to ask permission from or provide credit to the photographer 
or Unsplash, although it is appreciated when possible.
https://unsplash.com/license

dog.jpg from https://unsplash.com/photos/1oGA2dCmd3A
Photo by Ana Minella on Unsplash

fox.jpg from https://unsplash.com/photos/XHK0JdmJxJc
Photo by Nathan Anderson on Unsplash

    
