# AFreeSvg
一个可以在安卓上绘制svg图片的库
用法类似安卓canvas，通过SVGCanvas绘制图形，然后可以导出svg文件

## 使用方法
gradle添加jitpack仓库和依赖
```
 maven { url 'https://jitpack.io' }
 implementation 'com.github.feiyin0719:AFreeSvg:0.0.1-beta4'
```

## 参考代码
```java
        SVGCanvas svgCanvas = null;
        try {
            svgCanvas = new SVGCanvas(500, 500);
            SVGPaint paint = new SVGPaint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setFillColor(0xff0000ff);
            paint.setDashArray(new float[]{5, 5, 10});
            paint.setColor(Color.RED);
            paint.setStrokeWidth(2);
            svgCanvas.drawLine(10, 10, 200, 200, paint);

            SVGPaint paint1 = new SVGPaint();
            //线性渐变
            SVGLinearGradient svgLinearGradient = new SVGLinearGradient(new PointF(0, 0), new PointF(1, 0));
            svgLinearGradient.addStopColor(0, 0xffff0000);
            svgLinearGradient.addStopColor(0.5f, 0xff00ff00);
            svgLinearGradient.addStopColor(0.75f, 0xff00eeee);
            svgLinearGradient.addStopColor(1, 0xff0000ff);
            paint1.setGradient(svgLinearGradient);
            paint1.setStyle(Paint.Style.FILL_AND_STROKE);
            paint1.setStrokeWidth(2);
            paint1.setARGB(100, 200, 200, 0);

            svgCanvas.drawRect(new RectF(300, 300, 400, 450), paint1);
            svgCanvas.drawOval(new RectF(150, 150, 200, 200), paint1);
            SVGPaint paint2 = new SVGPaint();
            paint2.setStyle(Paint.Style.FILL);
            //放射渐变
            SVGRadialGradient gradient = new SVGRadialGradient(0.5f, 0.5f, 1, 0.8f, 0.8f);
            gradient.addStopColor(0.5f, 0xffff0000);
            gradient.addStopColor(1f, 0xff0000ff);
            paint2.setGradient(gradient);
            paint2.setFillRule(SVGPaint.FILL_RULE_EVENODD);
            //save功能
            svgCanvas.save();
            svgCanvas.translate(10, 10);
            //clipShape创建
            SVGShapeGroup clipGroup = new SVGShapeGroup();
            SVGPath clipPath = new SVGPath();
            clipPath.oval(0.2f, 0.2f, 0.2f, 0.2f);
            SVGPath clipPath1 = new SVGPath();
            clipPath1.oval(0.6f, 0.2f, 0.2f, 0.2f);
            clipGroup.addShape(clipPath);
            clipGroup.addShape(clipPath1);
            SVGClipShape clipShape = new SVGClipShape(clipGroup, SVGModes.MODE_BOX);
            svgCanvas.save();
            //clipShape设置
            svgCanvas.clip(clipShape);
            svgCanvas.drawRect(new RectF(300, 300, 400, 450), paint2);
            svgCanvas.save();
            SVGClipShape clipShape1 = new SVGClipShape(clipPath, SVGModes.MODE_BOX);
            svgCanvas.clip(clipShape1);
            svgCanvas.restore();
            //绘制多条线段
            svgCanvas.drawPolyline(new float[]{20, 20, 40, 25, 60, 40, 80, 120, 120, 140, 200, 180}, paint);
            svgCanvas.restore();
            //绘制多边形
            svgCanvas.drawPolygon(new float[]{100, 10, 40, 198, 190, 78, 10, 78, 160, 198}, paint2);
            //创建path
            SVGPath svgPath = new SVGPath();
            svgPath.moveTo(200, 200);
            svgPath.oval(200, 200, 50, 50);

            svgPath.rect(100, 50, 50, 50);

            svgPath.moveTo(100, 300);
            svgPath.quadraticBelzierCurve(150, 250, 200, 400);
            //绘制path
            svgCanvas.drawPath(svgPath, paint);
            //绘制贝塞尔曲线
            svgCanvas.drawCurve(50, 50, 200, 50, 100, 25, paint);
            //绘制圆弧
            svgCanvas.drawArc(300, 100, 50, 50, 90, 270, paint);

            SVGPath path = new SVGPath();
            path.rect(20, 20, 100, 400);
            svgCanvas.restore();
            SVGShapeGroup group = new SVGShapeGroup();
            group.addShape(path);
            group.addShape(svgPath);
            //绘制shape
            svgCanvas.drawShape(group, paint);

            //绘制文字
            SVGPaint textPaint = new SVGPaint();
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setGradient(svgLinearGradient);
            textPaint.setFont(new SVGFont.Builder().setFontFamily("sans-serif")
                    .setFontStyle(SVGFont.STYLE_ITALIC)
                    .setFontWeight("bold")
                    .build());
            svgCanvas.drawText("hello world", 200, 20, textPaint, "");

            SVGPath textPath = new SVGPath();
            textPath.oval(100, 400, 100, 100);
            svgCanvas.drawTextOnPath("hello", 0, 0, 0, 0, textPath, textPaint, null);
            svgCanvas.drawTextOnPath("world", 0, 0, 50, 0, textPath, textPaint, null);

            svgCanvas.drawPath(textPath, paint);
            String s = svgCanvas.getSVGXmlString();
            Log.i("myyf", s);
            File file =new File(getExternalCacheDir(),"test.svg");
            svgCanvas.writeSVGXMLToStream(new FileOutputStream(file));


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
```

生成图片如下
![](https://raw.githubusercontent.com/feiyin0719/AFreeSvg/806b6c907157668835a07b1b11ba80be6df226de/test.svg)
## api介绍
- **SVGCanvas**

绘制canvas，使用方法类似安卓canvas。

图形绘制api 
   1. drawRect(RectF rectF, SVGPaint paint) //绘制矩形
   2. drawLine(float x1, float y1, float x2, float y2, SVGPaint paint) //绘制线段
   3. drawOval(RectF rectF, SVGPaint paint) //绘制椭圆或者圆
   4. drawPolygon(float[] points, SVGPaint paint) //绘制多边形
   5. drawPolyline(float points[], SVGPaint paint) //绘制多线段 
   6. public void drawArc(float x, float y, float width, float height, float startAngle,float arcAngle, SVGPaint paint) //绘制圆弧
   7. public void drawCurve(float sx, float sy, float ex, float ey, float x, float y, SVGPaint paint) //绘制贝塞尔曲线
   8. public void drawPath(SVGPath path, SVGPaint paint) //绘制普通path
   9. public void drawShape(SVGShape shape, SVGPaint paint) //绘制shape
   10. public void clip(SVGClipShape clipShape) //设置裁剪区域
   11. public void drawText(String text, float x, float y, SVGPaint paint)//绘制文本
   12. public void drawTextOnPath(String text, float x, float y, SVGPath path, SVGPaint paint) //绘制文本在path上

- **SVGPaint**

绘制画笔类，继承自安卓paint，主要用来添加一些安卓paint不支持的功能，以及解决无法获取渐变色和dash的问题
   1. setDashArray(float[] dashArray) //设置线段dash值
   2. setGradient(SVGGradient gradient) //设置渐变色
   3. setFillRule(String fillRule) //设置填充规则 值意义参考svg    nonzero / evenodd / inherit 默认nonzero

- **SVGGradient**

渐变色
  1. SVGLinearGradient  //线性渐变
  2. SVGRadialGradient //放射渐变
- **SVGShape**
 1. SVGPath //设置path路径
 2. SVGShapeGroup //shape组，可以同时绘制多个shape 对应于 svg的g
- **SVGClipShape**

设置裁剪区域
  
## 版本日志
- **0.0.1-beta5**

 1. 增加绘制文本能力
 2. 增加字体类
- **0.0.1-beta4**
 1. 支持分别设置fillColor和strokeColor
 2. 增加渐变填充模式支持
 3. 增加绘制贝塞尔曲线 圆弧 path 支持
 4. 增加clip支持
 5. 增加save restore支持
 6. 重构部分代码实现 修复一些bug
- **0.0.1-beta3**

 1. 新增渐变色支持
 2. 新增绘制多边形、绘制多线段支持
 3. 重构paint实现，新增SVGPaint类，用来解决安卓自带Paint无法获取dash和渐变值
- **0.0.1-beta2**
 1. 解决描边和填充默认颜色问题
- **0.0.1-beta1**
 1. 支持线段、矩形、圆形、椭圆的绘制填充
 2. 支持transform

目前还处于开发阶段，后续会支持更多功能
