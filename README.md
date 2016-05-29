NumberMorphView4Android
======
**NumberMorphView** for Android.<br/>
Based on (https://github.com/me-abhinav/NumberMorphView)

#### Screenshot
![](https://github.com/Even201314/NumberMorphView4Android/blob/master/raw/master/screenshot/sample.gif) 


### Download
First, Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```
Then,Add the dependency:
```groovy
compile 'com.github.Even201314:NumberMorphView4Android:1.2.0'
```

## Usage
* XML

Add TimerView to the xml file :
```groovy
        <com.even.numbermorphview.TimerView
            android:id="@+id/numberMorphView"
            android:layout_width="52dp"
            android:layout_height="96dp"
            even:numBackgroundColor="#B6B6B6"
            even:numColor="#5D4037"
            even:strokeCap="SQUARE"
            even:strokeJoin="BEVEL" />
```
there are optional attributes to personalize the interface :

Color : numColor, numBackgroundColor;

Dimension : strokeWidth;

Paint Style : strokeCap(BUTT,ROUND,SQUARE), strokeJoin(MITER,ROUND,BEVEL);

* Code

```groovy
            numberMorphView.interpolator = new TimerView.LinearInterpolator();
            numberMorphView.setPeriod(1000);
```
We support six Interpolators :

LinearInterpolator, OvershootInterpolator, SpringInterpolator,
BounceInterpolator, AnticipateOvershootInterpolator, CubicHermiteInterpolator;

setPeriod() to set the interval time of animation;

In this vesion 1.2.0, I use handler and message to achieve the timing, the code is in [sample](https://github.com/Even201314/NumberMorphView4Android/blob/master/sample/src/main/java/com/even/sample/MainActivity.java).

## License
NumberMorphView4Android is available under the MIT license. See the LICENSE file for more info.

## Version 
* Version 1.2.0
