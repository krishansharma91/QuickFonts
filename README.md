QuickFont
=======

Android library to apply quick custom typefaces directly from layouts.The library maintains a cache of least recently used typefaces.

Usage
-----

Here is an example of the use of this library.It contains commonly used widgets ,where you can apply typeface--

```xml

   <com.quick.TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/hello_world"
        android:textColor="#2cc26b"
        android:textSize="22sp"
        app:quickfont="JennaSue.ttf" 
        />
```       
##### FloatinglabelView based on Hardik's FloatingLabel

```xml

  <com.quick.FloatingLabelView
        android:id="@+id/label1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        floatview:floatHintText="Username"
        floatview:textColor="@android:color/black"
        floatview:textTypeface="JennaSue.ttf"
        floatview:floatHintTextTypeface="JennaSue.ttf"
        floatview:floatHintTextColorFocused="@color/blue"
        floatview:floatHintTextColorUnFocused="@android:color/secondary_text_light_nodisable"
        floatview:floatSingleLine="true"
        floatview:floatInputType="text"
        floatview:floatHintTextGravity="left"
        floatview:floatImeOptions="actionNext"
        />
```       


###### Supported Widgets:
* [FloatingLabel](https://github.com/hardik-trivedi/FloatingLabel)
* [TextView](http://developer.android.com/reference/android/widget/TextView.html)
* [EditText](http://developer.android.com/reference/android/widget/EditText.html)
* [Button](http://developer.android.com/reference/android/widget/Button.html)
* [RadioButton](http://developer.android.com/guide/topics/ui/controls/radiobutton.html)
* [CheckBox](http://developer.android.com/guide/topics/ui/controls/checkbox.html)
* [AutoCompleteTextView](http://developer.android.com/reference/android/widget/AutoCompleteTextView.html)
* [CheckedTextView](http://developer.android.com/reference/android/widget/CheckedTextView.html)
* [ToggleButton](http://developer.android.com/reference/android/widget/ToggleButton.html)

### Need to use in your own custom View
you can do something like-

```java
Typeface typeface=QuickFontManager.getTypeface(getApplicationContext(), "Font.ttf").first;

```
This will also add the typeface into its cache if not already there.


### Few Configurations (Optional)

You can configure the cache size according to your need. Debuggable "true" will turn your view to red if typeface not fetched from cache.Use this in your splash or Application class.

```java
        new QuickFontManager.QuickFontBuilder()
        .setDebuggable(true)
        .setCachesize(2)
        .build();
        
```


