# AutoLayout

利用运行时注解为`Activity`和`Fragent`注入布局。

## Activity

### 自动注入

1. 在`Application`中调用`application.registerAutoLayout()`进行注册。
2. 为activity添加`AutoLayout`注解，其中`value`配置该activity的布局文件ID，`autoInject`配置为`true`（默认值为`true`）。

```kotlin
@AutoLayout(R.layout.activity_auto_layout)
class AutoLayoutExampleActivity : AppCompatActivity()
```

### 手动注入

1. 为activity添加`AutoLayout注解`，其中`value`配置该activity的布局文件ID。若此时已经启用了自动注入，`autoInject`需要显式配置为`false`；若未启用自动注入，`autoInject`可任意配置。
2. 在activity的`onCreate()`方法中调用`injectAutoLayout()`方法。

```kotlin
@AutoLayout(
        value = R.layout.activity_manual_layout,
        autoInject = false
)
class ManualLayoutExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectAutoLayout()
    }
}
```

## Fragment

### 自动注入

1. fragment继承`AutoLayoutFragment`。
2. 为fragment添加`AutoLayout`注解，其中`value`配置为该fragment的布局文件ID，`autoInject`可任意配置。

```kotlin
@AutoLayout(R.layout.fragment_auto_layout)
class AutoLayoutExampleFragment : AutoLayoutFragment()
```

### 手动注入

重写fragment的`onCreateView()`方法，直接调用`onAutoCreateView()`返回或调用`getInjectLayoutId()`方法获取布局ID自行创建View返回。

```kotlin
@AutoLayout(R.layout.fragment_auto_layout3)
class AutoLayoutExampleFragment3 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?) = onAutoCreateView(inflater, container)
}
```
或
```kotlin
class AutoLayoutExampleFragment4 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        @LayoutRes val layoutId = getInjectLayoutId()
        if (layoutId <= 0) {
            return null
        }
        return inflater.inflate(layoutId, container, false)
    }
}
```

