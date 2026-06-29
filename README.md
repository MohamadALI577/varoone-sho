# وارونه شو (Varooneh Sho)

بازی مهمونی فارسی — راستشو برعکس بگو! 🎉

## ✨ ویژگی‌ها

### ۵ حالت بازی
- ⛓ **کلاسیک**: نوبتی، ساده، مناسب همه
- 💣 **بمب‌داغ**: گوشی دست‌به‌دست با دکمه‌های بله/خیر — پاسخ اشتباه ۲-۳ ثانیه از بمب کم می‌کنه!
- 🔥 **صندلی داغ**: یک نفر وسط، بقیه داور
- ⚔️ **نبرد تیمی پیشرفته**: دو تیم با نام قابل ویرایش، چند سوال در هر دست، اعلام نوبت
- 🌀 **آشوب**: پاورآپ‌های تصادفی

### بانک سوالات گسترده
- **۱۶۰۲ سوال** در ۶ دسته: عمومی، تکنولوژی، علمی، ورزشی، تاریخی، ویدیوگیمی
- ۴ سطح سختی متعادل: آسان (پیش‌فرض)، متوسط، سخت، خبره
- حالت ترکیبی (همه دسته‌ها) برای تنوع بیشتر

### موسیقی و صدا
- ۶ موسیقی متن اختصاصی (یکی برای هر حالت + منوی اصلی)
- Crossfade نرم بین موسیقی‌ها
- موسیقی در طول کارت چالش متوقف و سپس ادامه پیدا می‌کند
- حجم موسیقی و افکت صوتی به‌طور جداگانه قابل تنظیم (پیش‌فرض ۵۰٪ موسیقی، ۷۰٪ افکت)

### رابط کاربری مدرن
- منوی اصلی با لوگوی اختصاصی استودیو
- جریان روان: منوی بازیکن → انتخاب حالت → تنظیمات حالت → آماده → بازی
- هر حالت منوی مستقل خودش را دارد
- پشتیبانی کامل از زبان فارسی و انگلیسی (تغییر زنده RTL/LTR)
- اسکرول روان در تمام صفحات موبایل
- دکمه افزودن بازیکن کوچک، input بزرگ و اصلی

### تنظیمات قابل تنظیم
- رنگ تم (۷ رنگ)
- حالت شب/روز
- زبان (فارسی/انگلیسی)
- افکت صوتی + حجم
- موسیقی + حجم
- لرزش
- ذخیره آمار
- پاک‌کردن داده‌ها
- وب‌سایت استودیو و حمایت مالی

## 📁 ساختار پروژه

```
varooneh/
├── index.html          # فایل اصلی بازی (تمام HTML/CSS/JS)
├── music/              # فایل‌های موسیقی
│   ├── home.mp3        # موسیقی منوی اصلی
│   ├── classic.mp3     # موسیقی حالت کلاسیک
│   ├── bomb.mp3        # موسیقی حالت بمب‌داغ
│   ├── seat.mp3        # موسیقی حالت صندلی داغ
│   ├── team.mp3        # موسیقی حالت تیمی
│   └── chaos.mp3       # موسیقی حالت آشوب
├── assets/
│   ├── logo.png        # لوگوی بازی (پس‌زمینه شفاف)
│   └── logo-small.png  # نسخه کوچک لوگو
├── res/
│   ├── icon/android/icon.png    # آیکون اندروید
│   └── screen/android/splash.png # صفحه اسپلش اندروید
├── config.xml          # تنظیمات Cordova
└── README.md           # این فایل
```

## 🎮 اجرای محلی (تست)

کافی است فایل `index.html` را در مرورگر باز کنید. تمام امکانات کار می‌کند.

```bash
# یا با یک سرور ساده:
python3 -m http.server 8080
# سپس در مرورگر: http://localhost:8080
```

## 📱 ساخت APK با Cordova

### پیش‌نیازها

1. **Node.js** (نسخه ۱۶ یا بالاتر) — از [nodejs.org](https://nodejs.org/)
2. **Java JDK 11** — برای کامپایل اندروید
3. **Android Studio** یا **Android SDK Command-line Tools**
4. **Gradle** (به‌صورت خودکار توسط Cordova نصب می‌شود)

### مراحل نصب Cordova

```bash
# نصب Cordova به‌صورت سراسری
npm install -g cordova@latest

# بررسی نصب
cordova --version
```

### ساخت پروژه و افزودن پلتفرم

```bash
# ۱. یک پروژه Cordova جدید بسازید
cordova create varooneh-app io.gabas.varoonehsho "وارونه شو"

# ۲. وارد پوشه پروژه شوید
cd varooneh-app

# ۳. پلتفرم اندروید را اضافه کنید
cordova platform add android

# ۴. افزونه‌های مورد نیاز را نصب کنید
cordova plugin add cordova-plugin-whitelist
cordova plugin add cordova-plugin-vibration
```

### جایگزینی محتوا با فایل‌های بازی

```bash
# فایل index.html، پوشه music، assets را از پروژه وارونه شو
# در پوشه www پروژه Cordova کپی کنید:

# از پوشه پروژه وارونه شو:
cp /path/to/varooneh/index.html /path/to/varooneh-app/www/
cp -r /path/to/varooneh/music /path/to/varooneh-app/www/
cp -r /path/to/varooneh/assets /path/to/varooneh-app/www/

# فایل config.xml را نیز جایگزین کنید:
cp /path/to/varooneh/config.xml /path/to/varooneh-app/

# آیکون و اسپلش:
cp -r /path/to/varooneh/res /path/to/varooneh-app/
```

ساختار نهایی پوشه `www`:
```
www/
├── index.html
├── music/
│   ├── home.mp3
│   ├── classic.mp3
│   ├── bomb.mp3
│   ├── seat.mp3
│   ├── team.mp3
│   └── chaos.mp3
├── assets/
│   ├── logo.png
│   └── logo-small.png
└── cordova.js    (خودکار توسط Cordova اضافه می‌شود)
```

### ساخت APK

```bash
# بررسی پیش‌نیازها
cordova requirements android

# ساخت APK (نسخه debug — برای تست)
cordova build android

# خروجی در این مسیر تولید می‌شود:
# platforms/android/app/build/outputs/apk/debug/app-debug.apk
```

### ساخت APK نسخه Release (برای انتشار)

```bash
# ۱. یک keystore بسازید (یک‌بار برای همیشه)
keytool -genkey -v -keystore varooneh.keystore -alias varooneh -keyalg RSA -keysize 2048 -validity 10000

# ۲. فایل build.json در ریشه پروژه Cordova بسازید:
cat > build.json << 'BUILDJSON'
{
  "android": {
    "release": {
      "keystore": "varooneh.keystore",
      "storePassword": "YOUR_STORE_PASSWORD",
      "alias": "varooneh",
      "password": "YOUR_KEY_PASSWORD",
      "keystoreType": ""
    }
  }
}
BUILDJSON

# ۳. APK نسخه release بسازید
cordova build android --release

# خروجی:
# platforms/android/app/build/outputs/apk/release/app-release.apk
```

### نصب روی گوشی

```bash
# با USB به کامپیوتر وصل شوید و حالت Developer را فعال کنید
cordova run android

# یا APK را مستقیم روی گوشی کپی و نصب کنید
adb install platforms/android/app/build/outputs/apk/debug/app-debug.apk
```

## 🐛 رفع مشکلات رایج

### موسیقی در موبایل پخش نمی‌شود
- مرورگرهای موبایل اجازه پخش خودکار صدا را نمی‌دهند تا کاربر تعامل کند.
- در کد، صدا روی اولین `touchstart` یا `click` فعال می‌شود.
- اگر باز هم کار نکرد، مطمئن شوید که فایل‌های MP3 در مسیر `www/music/` قرار دارند.

### مشکل اسکرول در موبایل
- این مشکل در نسخه جدید رفع شده است.
- تمام صفحات بلند از کلاس `scroll-y` استفاده می‌کنند که `-webkit-overflow-scrolling: touch` را فعال می‌کند.
- صفحه بازیکنان به‌گونه‌ای طراحی شده که حداقل اسکرول نیاز دارد.

### تغییر زبان کار نمی‌کند
- مطمئن شوید که فایل JS به‌درستی بارگذاری شده.
- در کنسول مرورگر خطاها را بررسی کنید.
- زبان باید بین `fa` و `en` سوییچ کند و direction را بین RTL/LTR تغییر دهد.

### خطای "Could not find an installed version of Gradle"
```bash
# در Android Studio:
# File → Settings → Build, Execution, Deployment → Build Tools → Gradle
# مسیر Gradle را مشخص کنید
```

### خطای SDK
```bash
# نصب SDK Manager
sdkmanager "platform-tools" "platforms;android-33" "build-tools;33.0.0"

# تنظیم ANDROID_HOME
export ANDROID_HOME=/path/to/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
```

## 🎨 سفارشی‌سازی

### تغییر رنگ تم
در تنظیمات بازی، از بین ۷ رنگ تم یکی را انتخاب کنید.

### افزودن سوال جدید
در فایل `index.html`، آبجکت `QUESTIONS` را پیدا کرده و سوال‌های جدید اضافه کنید:
```javascript
const QUESTIONS = {
  general: {
    easy: [
      ['متن سوال', true, 'English text'],  // true = جواب واقعی بله است
      // ...
    ]
  }
};
```

### افزودن موسیقی جدید
فایل MP3 را در پوشه `music/` قرار دهید و در `MUSIC_TRACKS` در فایل `index.html` مسیر را اضافه کنید.

## 📞 پشتیبانی

- تلگرام: @GABASsupport
- سایت: [gabas-studio.netlify.app](https://gabas-studio.netlify.app)
- حمایت مالی: [reymit.ir/gabas.studio](https://reymit.ir/gabas.studio)

## 📜 لایسنس

© ۲۰۲۴ GABAS Studio. تمام حقوق محفوظ است.

این بازی برای استفاده شخصی و غیرتجاری رایگان است.
برای استفاده تجاری، لطفاً با ما تماس بگیرید.
