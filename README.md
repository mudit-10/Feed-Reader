# Feed-Reader
Feed-Reader is an Android App which reads the RSS feed(written in XML) from a blogging website through Retrofit <br>
and displays it in list views arranged in various tabs. Each tab represents a different tag of the website. Fragments have been used alongwith viewpager, with the help of <br> FragmentPagerAdapter. The coordinator layout has been used to give the effect of a collapsng toolbar. JSoup has been used to handle the html content of the website included in one of the tags of the XML. <br>

## Prerequisites

The following are some of the specifications of the project <br>
- compileSdkVersion : 25 <br>
- buildToolsVersion : 25.0.2 <br>

The following libraries should be included in the build.gradle(module) before compilation

#### 1. Coordinator Layout
`compile 'com.android.support:design:25.3.1' `
        
#### 2. Universal Image Loader
`compile 'com.nostra13.universalimageloader:universal-image-loader:1.9.5'`

#### 3. Retrofit
`compile 'com.squareup.retrofit2:retrofit:2.3.0'`

#### 4. Simple XML convertor Retrofit
```
compile ('com.squareup.retrofit2:converter-simplexml:2.3.0')
            {
                exclude group: 'xpp3', module: 'xpp3'
                exclude group: 'stax', module: 'stax-api'
                exclude group: 'stax', module: 'stax'
            }
```

#### 5. OkHTTP
`compile 'com.squareup.okhttp3:okhttp:3.8.1'`

#### 6. JSoup
`compile "org.jsoup:jsoup:1.8.1"`
