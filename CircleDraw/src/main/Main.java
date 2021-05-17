package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author WilliamLi
 * @version 1.0
 * @date 2021/5/15 21:44
 */
public class Main {

    private static Point getPoint(int begX, int begY, BufferedImage bf,
                                  int tarR, int tarG, int tarB, int erro){
        Point result;
        int high = bf.getHeight();
        int width = bf.getWidth();
        for (int i=begX;i < width;i++) {
            for (int j = begY; j < high; j++) {
                int tempRGB = bf.getRGB(i, j);
                int alpha = (tempRGB >> 24) & 0xff;
                int red   = (tempRGB >> 16) & 0xff;
                int green = (tempRGB >>  8) & 0xff;
                int blue  = (tempRGB      ) & 0xff;
                if (Math.abs(red-tarR)<erro && Math.abs(green-tarG)<erro && Math.abs(blue-tarB)<erro) {
                    Main main = new Main();
                    result = main.new Point(i,j);
                    return result;
                }
            }
        }
        return null;
    }

    private static void fileWrite(FileOutputStream fo,String str) throws IOException{
        byte[] b = str.getBytes(StandardCharsets.UTF_8);
        fo.write(b);
        fo.flush();
    }

    private class Point{

        private int x;
        private int y;

        public Point(int x,int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        BufferedImage bf = null;
        File outPutFile = null;
        FileOutputStream fileOutputStream = null;
        int time = 0;
        System.out.println("请输入扫描的图片的绝对路径");
        String path = input.nextLine();
        System.out.println("请输入希望扫描的点的RGB中红色的10进制数");
        int tarR = input.nextInt();
        input.nextLine();
        System.out.println("请输入希望扫描的点的RGB中绿色的10进制数");
        int tarG = input.nextInt();
        input.nextLine();
        System.out.println("请输入希望扫描的点的RGB中蓝色的10进制数");
        int tarB = input.nextInt();
        input.nextLine();
        System.out.println("请输入RGB误差值");
        int erro = input.nextInt();
        input.nextLine();
        System.out.println("请输入希望画的点的颜色代号（1：黑色  2：白色）");
        int color = input.nextInt();

        try {
            bf = ImageIO.read(new File(path));
            Point p1   = getPoint(0,0,bf,tarR,tarG,tarB,erro);
            Point p2   = getPoint(p1.getX()+1,0,bf,tarR,tarG,tarB,erro);
            Point p3   = getPoint(p2.getX()+1,0,bf,tarR,tarG,tarB,erro);
            long a     = p1.getX();
            long b     = p1.getY();
            long c     = p2.getX();
            long d     = p2.getY();
            long e     = p3.getX();
            long f     = p3.getY();
            long upper = (e*e+f*f-c*c-d*d) * (2*d-2*b) - (c*c+d*d-a*a-b*b) * (2*f-2*d);
            long lower = (2*a-2*c) * (2*f-2*d) - (2*c-2*e) * (2*d-2*b);
            long x     = Math.round((double) upper/lower);
            upper      = c*c + d*d - a*a - b*b + (2*a - 2*c) * x;
            lower      = (2*d - 2*b);
            long y     = Math.round((double) upper/lower);
            long r     = (long) Math.sqrt((x-a) * (x-a) + (y-b) * (y-b));
            double area = Math.PI * r * r;
            double peri = Math.PI * 2 * r;

            switch (color){
                case 1:
                    color = 0x00000000;
                    break;
                case 2:
                    color = 0xffffffff;
                    break;
                case 3:
                    color = 0x0000ffff;
                    break;
                case 4:
                    color = 0x00ff00ff;
                    break;
                case 5:
                    color = 0x00ffff00;
                    break;
                default:
                    color = 0x00000000;
            }
            bf.setRGB((int) x, (int) y, color);

            System.out.println("圆心：("+x+", "+y+")");
            System.out.println("半径："+r);
            System.out.println("面积："+area);
            System.out.println("周长："+peri);

            String simpleName = path.substring(path.lastIndexOf("\\")+1);
            String simplePath = path.substring(0, path.lastIndexOf("\\"));
            String Name = simpleName.substring(0, simpleName.lastIndexOf("."));
            String resultPath = simplePath + "result-" + simpleName;
            String textPath = simplePath + Name + "-result.txt";
            outPutFile = new File(textPath);
            fileOutputStream = new FileOutputStream(outPutFile,false);
            fileWrite(fileOutputStream,"Number:             " + ++time +"\n");
            fileWrite(fileOutputStream,"Out put path:       " + resultPath + "\n");
            fileWrite(fileOutputStream,"Center:             (" + x + ", " + y + ")\n");
            fileWrite(fileOutputStream,"Radius:             " + r + "\n");
            fileWrite(fileOutputStream,"Area:               " + area + "\n");
            fileWrite(fileOutputStream,"Perimeter:          " + peri + "\n");
            fileWrite(fileOutputStream,"------------------------------------------");
            ImageIO.write(bf,"JPEG",new File(resultPath));
            System.out.println("文件写入完成");
        }catch (FileNotFoundException e){
            e.printStackTrace(System.err);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        } catch (Exception e){
            e.printStackTrace(System.err);
        } finally {
            try {
                if (fileOutputStream != null){
                    fileOutputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
