# MXStarter

[![](https://jitpack.io/v/com.gitee.zhangmengxiong/MXStarter.svg)](https://jitpack.io/#com.gitee.zhangmengxiong/MXStarter)

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
