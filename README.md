# MXStarter


最新版本：[![](https://jitpack.io/v/com.gitee.zhangmengxiong/MXStarter.svg)](https://jitpack.io/#com.gitee.zhangmengxiong/MXStarter)
```groovy
    implementation 'com.gitee.zhangmengxiong:MXStarter:1.2.4'
```

### 解决的问题
Android开发中打开Activity并获取结果的原生方法：
```kotlin
    // 启动页面
    startActivityForResult(Intent(this,XXX::class.java), 0x22)

    // 获取结果
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0x22){
            // 这里获取结果
        }
    }
```
当调用多个启动页面时，需要一个个判断requestCode并多次编写if xxx else xxx

使用MXStarter后，调用Activity方法如下：
```kotlin
MXStarter.start(this, ResultActivity::class.java) { resultCode: Int, data: Intent? ->
    // 这里获取结果
}
```

### 权限申请功能集成
在Activity和Fragment中可以直接获取权限申请回调，不需要再重载Activity的 onRequestPermissionsResult() 方法获取结果
```kotlin
MXPermission.requestPermission(
            activity,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) { allowed ->
            Toast.makeText(
                this@MainActivity,
                "获取权限：$allowed",
                Toast.LENGTH_SHORT
            ).show()
        }
```