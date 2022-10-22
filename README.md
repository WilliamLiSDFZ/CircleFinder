# CircleFinder
This is a Java program to calculator the center of a circle in a photo by scanning the three point on the circle.

## How to use
First, use Photoshop to draw three dot with the same color. The three dot should be in the size of one pixel and couldn't have the same color with other part of the picture. 
(首先在一张希望扫描的图片上用Photoshop画三个相同颜色的点，这三个点要求大小是一个像素，并且颜色不能在图片中和其他部分相同。)

If other area of the photo have the same color with the dot, please cover those area with black color. Otherwise, it will affect the accuracy of the algorithm.
(如果有相同的颜色，请用黑色覆盖，否则会影响算法。)

Run the program; input the location of the picture; input the hexadecimal number of RGB color of the three dots and accuracy.
(运行程序，需要输入希望扫描的图片的路径，三个点的RGB三个十进制数，还有误差值。)

I highly remommend to set the accuracy to 5.
(误差值建议使用5.)

The program will find the circle linked by the three dots, as well as it's center,  radius, area, and primeter. The program will generate a log file, and draw a dot on the photo to represent the center of the circle.
(程序会寻找三个点组成的正圆的圆心，半径，面积，周长。并且生成日志文件。同时会在图片上画出一个点表示圆心。)
