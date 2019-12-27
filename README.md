# react-native-yandex-kassa

## Getting started

`$ npm install react-native-yandex-kassa --save`

### Mostly automatic installation

`$ react-native link react-native-yandex-kassa`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-yandex-kassa` and add `YandexKassa.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libYandexKassa.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.reactlibrary.YandexKassaPackage;` to the imports at the top of the file
  - Add `new YandexKassaPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-yandex-kassa'
  	project(':react-native-yandex-kassa').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-yandex-kassa/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-yandex-kassa')
  	```


## Usage
```javascript
import YandexKassa from 'react-native-yandex-kassa';

// TODO: What to do with the module?
YandexKassa;
```
