# ReportPlus+
A fork of [ReportPlus](https://github.com/xBoneso/ReportPlus) with improvements and maintenance


[![](https://jitpack.io/v/Fernthedev/ReportPlus.svg)](https://jitpack.io/#Fernthedev/ReportPlus)
[![CircleCI](https://circleci.com/gh/Fernthedev/ReportPlus/tree/master.svg?style=svg)](https://circleci.com/gh/Fernthedev/ReportPlus/tree/master)
[![JitCI](https://jitci.com/gh/Fernthedev/ReportPlus/svg)](https://jitci.com/gh/Fernthedev/ReportPlus)

## API
### Maven
```xml
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>
```
```xml
<dependency>
    <groupId>com.github.Fernthedev</groupId>
    <artifactId>ReportPlus</artifactId>
    <version>Tag</version>
  <scope>provided</scope>
</dependency>
```
### Gradle
```java
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 ```
 ```java
 	dependencies {
	        implementation 'com.github.Fernthedev:ReportPlus:Tag'
	}
  ```
  
To use the API, just use
```java
ReportPlusAPI.getApi().
```
There's not much to say, though if you do need help just make a github issue and it will be answered as soon as possible
If you need other methods of contacting, use the original discord server for the original project if it's still up
