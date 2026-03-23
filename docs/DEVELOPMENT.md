# 开发文档

本文档包含冰箱管家应用的开发环境搭建、代码规范和架构说明。

## 🚀 开发环境搭建

### 系统要求

- **操作系统**：Windows 10/11、macOS 10.14+、Linux (Ubuntu 18.04+)
- **内存**：8GB RAM（推荐 16GB）
- **磁盘空间**：至少 10GB 可用空间
- **JDK**：JDK 11 或更高版本
- **Android Studio**：最新稳定版（推荐 Hedgehog 或更高）

### 安装步骤

#### 1. 安装 JDK

```bash
# macOS (使用 Homebrew)
brew install openjdk@17

# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-17-jdk

# Windows
# 下载并安装 JDK 17：https://www.oracle.com/java/technologies/downloads/
```

#### 2. 配置环境变量

```bash
# macOS/Linux
export JAVA_HOME=/path/to/jdk
export PATH=$PATH:$JAVA_HOME/bin

# Windows（系统环境变量）
JAVA_HOME = C:\Program Files\Java\jdk-17
PATH += %JAVA_HOME%\bin
```

#### 3. 安装 Android Studio

1. 下载：https://developer.android.com/studio
2. 安装过程中选择：
   - Android SDK
   - Android SDK Platform
   - Android Virtual Device (可选)

#### 4. 配置 Android SDK

```bash
# 打开 Android Studio → Settings → Appearance & Behavior → System Settings → Android SDK
# 安装以下组件：
- Android SDK Platform 34
- Android SDK Build-Tools 34
- Android SDK Command-line Tools
- Android Emulator (可选)
```

#### 5. 克隆项目

```bash
git clone https://github.com/yourusername/FridgeManager.git
cd FridgeManager
```

#### 6. 使用 Android Studio 打开

```bash
# 方式1：命令行
studio .

# 方式2：Android Studio → File → Open → 选择项目目录
```

#### 7. 同步项目

打开项目后，Android Studio 会自动开始 Gradle 同步。如果未自动开始：
- 点击 `File → Sync Project with Gradle Files`
- 或点击工具栏的同步图标

#### 8. 运行项目

```bash
# 方式1：Android Studio
# 点击 Run 按钮（绿色三角形）或按 Shift+F10

# 方式2：命令行
./gradlew installDebug
```

### 常见问题

#### Gradle 同步失败

```bash
# 清除 Gradle 缓存
./gradlew cleanBuildCache
./gradlew clean

# 重新同步
./gradlew build --refresh-dependencies
```

#### 找不到 SDK

检查 `local.properties` 文件：
```properties
sdk.dir=/path/to/Android/Sdk
```

#### 内存不足

编辑 `gradle.properties`：
```properties
org.gradle.jvmargs=-Xmx4096m -XX:MaxMetaspaceSize=512m
org.gradle.parallel=true
org.gradle.caching=true
```

---

## 📐 代码规范

### 代码风格

本项目遵循 [Kotlin 官方代码风格](https://kotlinlang.org/docs/coding-conventions.html) 和 [Android Kotlin 风格指南](https://developer.android.com/kotlin/style-guide)。

#### 命名规范

| 类型 | 规范 | 示例 |
|------|------|------|
| 包名 | 全小写，用点分隔 | `com.fridge.app.data.model` |
| 类/接口 | PascalCase | `IngredientRepository` |
| 函数 | camelCase | `getIngredientsByCategory()` |
| 变量 | camelCase | `totalCount` |
| 常量 | UPPER_SNAKE_CASE | `MAX_RETRY_COUNT` |
| 资源文件 | snake_case | `fragment_statistics.xml` |
| ID | snake_case | `tv_total_count` |

#### 文件组织

```
com/fridge/app/
├── data/                    # 数据层
│   ├── dao/                 # 数据库访问对象
│   ├── db/                  # 数据库定义
│   ├── local/               # 本地数据源
│   ├── model/               # 数据模型
│   └── repository/          # 仓库层
├── ui/                      # UI层
│   ├── activity/            # Activity
│   ├── adapter/             # 适配器
│   ├── fragment/            # Fragment
│   └── viewmodel/           # ViewModel
├── utils/                   # 工具类
├── worker/                  # 后台任务
├── MainActivity.kt
└── FridgeApplication.kt
```

#### 代码格式

使用 Android Studio 的默认格式化设置：
- 缩进：4 个空格
- 最大行宽：120 字符
- 自动导入组织

快捷键：
- Windows/Linux：`Ctrl+Alt+L`
- macOS：`Cmd+Option+L`

### 注释规范

#### 文件头注释

```kotlin
/**
 * 文件名：简要说明
 * 
 * 详细描述文件的功能和用途
 * 
 * @author 作者名
 * @since 版本号
 */
```

#### 类注释

```kotlin
/**
 * 食材数据仓库
 * 
 * 负责食材数据的获取、存储和管理。
 * 提供对本地数据库和可能的远程数据源的统一访问接口。
 * 
 * @property ingredientDao 食材数据库访问对象
 */
class IngredientRepository(private val ingredientDao: IngredientDao) {
    // ...
}
```

#### 函数注释

```kotlin
/**
 * 获取分类统计数据
 * 
 * 统计每种分类的食材数量和占比，用于饼图展示。
 * 
 * @return 分类统计列表，按数量降序排列
 * @throws Exception 数据库查询失败时抛出
 */
suspend fun getCategoryStatistics(): List<CategoryStatistics> {
    // ...
}
```

#### 参数和返回值注释

```kotlin
/**
 * 计算过期率
 * 
 * @param total 食材总数
 * @param expired 过期食材数
 * @return 过期率（0-100），当总数为0时返回0
 */
fun calculateExpiryRate(total: Int, expired: Int): Float {
    return if (total > 0) expired.toFloat() / total * 100 else 0f
}
```

### Kotlin 最佳实践

#### 空安全

```kotlin
// ✅ 推荐：使用 Elvis 操作符提供默认值
val count = nullableValue ?: 0

// ✅ 推荐：使用安全调用
val length = nullableString?.length

// ✅ 推荐：明确处理 null 情况
val result = nullableValue?.let { 
    process(it) 
} ?: defaultValue

// ❌ 避免：使用 !! 操作符
val length = nullableString!!.length  // 可能导致 NPE
```

#### 集合操作

```kotlin
// ✅ 推荐：使用高阶函数
val validIngredients = ingredients.filter { it.isValid() }
val totalCount = ingredients.sumOf { it.quantity }
val grouped = ingredients.groupBy { it.category }

// ✅ 推荐：使用作用域函数
val newIngredient = Ingredient(
    name = name,
    category = category
).apply {
    quantity = 1f
    addDate = Date()
}
```

#### 协程使用

```kotlin
class MyViewModel : ViewModel() {
    
    // ✅ 推荐：使用 viewModelScope 自动管理生命周期
    fun loadData() = viewModelScope.launch {
        val result = repository.fetchData()
        _data.value = result
    }
    
    // ✅ 推荐：使用 Dispatchers.IO 进行数据库操作
    suspend fun fetchFromDb(): List<Data> = withContext(Dispatchers.IO) {
        dao.getAll()
    }
    
    // ✅ 推荐：并发执行独立任务
    suspend fun loadStatistics() = coroutineScope {
        val overall = async { repository.getOverallStatistics() }
        val categories = async { repository.getCategoryStatistics() }
        
        StatisticsReport(
            overall = overall.await(),
            categories = categories.await()
        )
    }
}
```

#### Flow 使用

```kotlin
// ✅ 推荐：使用 Flow 处理数据流
fun getIngredientsFlow(): Flow<List<Ingredient>> = flow {
    emit(repository.getAll())
}
    .flowOn(Dispatchers.IO)
    .catch { e ->
        // 错误处理
        emit(emptyList())
    }
```

### 资源命名

#### 布局文件

| 类型 | 前缀 | 示例 |
|------|------|------|
| Activity | `activity_` | `activity_main.xml` |
| Fragment | `fragment_` | `fragment_statistics.xml` |
| Dialog | `dialog_` | `dialog_add_ingredient.xml` |
| Item (RecyclerView) | `item_` | `item_ingredient.xml` |
| Include | `include_` | `include_toolbar.xml` |

#### ID 命名

| 类型 | 前缀 | 示例 |
|------|------|------|
| TextView | `tv_` | `tv_title` |
| EditText | `et_` | `et_name` |
| Button | `btn_` | `btn_save` |
| ImageView | `iv_` | `iv_photo` |
| RecyclerView | `rv_` | `rv_ingredients` |
| ProgressBar | `pb_` | `pb_loading` |
| CardView | `cv_` | `cv_ingredient` |

#### 字符串资源

```xml
<!-- ✅ 推荐：使用有意义的键名 -->
<string name="dialog_title_add_ingredient">添加食材</string>
<string name="error_network_connection">网络连接失败，请检查网络设置</string>
<string name="format_ingredient_count">共 %1$d 种食材</string>

<!-- ❌ 避免：过于简短的键名 -->
<string name="title">标题</string>
<string name="error">错误</string>
```

---

## 🏗️ 架构说明

### MVVM 架构

```
┌─────────────────────────────────────────────────────────┐
│                         UI 层                            │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────┐ │
│  │  Activity   │  │  Fragment   │  │  Adapter        │ │
│  └──────┬──────┘  └──────┬──────┘  └─────────────────┘ │
│         │                │                              │
│         └────────────────┘                              │
│                  │                                      │
│                  ▼                                      │
│  ┌─────────────────────────────────────────────────┐   │
│  │              ViewModel                           │   │
│  │  - 持有 UI 数据（LiveData/StateFlow）            │   │
│  │  - 处理 UI 逻辑                                  │   │
│  │  - 调用 Repository 获取数据                      │   │
│  └────────────────────┬────────────────────────────┘   │
└───────────────────────┬─────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────────┐
│                      Repository 层                       │
│  ┌─────────────────────────────────────────────────┐   │
│  │           IngredientRepository                  │   │
│  │  - 抽象数据来源                                  │   │
│  │  - 数据转换和映射                                │   │
│  │  - 单一数据源                                    │   │
│  └────────────────────┬────────────────────────────┘   │
└───────────────────────┬─────────────────────────────────┘
                        │
         ┌──────────────┴──────────────┐
         │                             │
         ▼                             ▼
┌─────────────────┐           ┌─────────────────┐
│   Local (Room)  │           │  Remote (API)   │
│  ┌───────────┐  │           │  （预留扩展）   │
│  │  Entity   │  │           └─────────────────┘
│  │  DAO      │  │
│  │  Database │  │
│  └───────────┘  │
└─────────────────┘
```

### 数据流

```
用户操作 → View → ViewModel → Repository → DataSource
                              ↓
LiveData/Flow ← 数据更新 ← Repository
     ↓
   观察者模式自动更新 UI
```

### 模块职责

#### UI 层 (Activity/Fragment)

- 负责视图渲染和用户交互
- 观察 ViewModel 的数据变化
- 不直接处理业务逻辑
- 生命周期感知

```kotlin
class StatisticsFragment : Fragment() {
    private val viewModel: StatisticsViewModel by viewModels()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 观察数据变化，自动更新 UI
        viewModel.categoryStats.observe(viewLifecycleOwner) { stats ->
            updatePieChart(stats)
        }
    }
    
    private fun updatePieChart(stats: List<CategoryStatistics>) {
        // 更新图表 UI
    }
}
```

#### ViewModel 层

- 持有 UI 相关数据
- 处理 UI 业务逻辑
- 不持有 View 的引用
- 生命周期长于 Activity

```kotlin
class StatisticsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: IngredientRepository
    
    // LiveData 供 UI 观察
    private val _categoryStats = MutableLiveData<List<CategoryStatistics>>()
    val categoryStats: LiveData<List<CategoryStatistics>> = _categoryStats
    
    fun loadStatistics() = viewModelScope.launch {
        _categoryStats.value = repository.getCategoryStatistics()
    }
}
```

#### Repository 层

- 单一数据源
- 抽象数据来源
- 业务逻辑处理
- 数据转换

```kotlin
class IngredientRepository(private val ingredientDao: IngredientDao) {
    suspend fun getCategoryStatistics(): List<CategoryStatistics> {
        val ingredients = ingredientDao.getAllIngredientsSync()
        return ingredients.groupBy { it.category }
            .map { (category, list) ->
                CategoryStatistics(
                    category = category,
                    count = list.size,
                    // ...
                )
            }
    }
}
```

#### Data 层 (Room)

- 数据持久化
- 数据库操作
- 数据模型定义

```kotlin
@Entity(tableName = "ingredients")
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    // ...
)

@Dao
interface IngredientDao {
    @Query("SELECT * FROM ingredients")
    suspend fun getAllIngredientsSync(): List<Ingredient>
}
```

### 依赖关系

```
UI → ViewModel → Repository → DAO → Database

禁止反向依赖！
- ViewModel 不应该知道 View 的存在
- Repository 不应该知道 ViewModel 的存在
- DAO 不应该知道 Repository 的存在
```

### 扩展性设计

#### 添加新的数据源

1. 创建接口定义数据源：
```kotlin
interface IngredientDataSource {
    suspend fun getAll(): List<Ingredient>
    suspend fun insert(ingredient: Ingredient)
}
```

2. 实现本地数据源：
```kotlin
class LocalIngredientDataSource(private val dao: IngredientDao) : IngredientDataSource {
    // 实现...
}
```

3. Repository 组合多个数据源：
```kotlin
class IngredientRepository(
    private val local: IngredientDataSource,
    private val remote: IngredientDataSource? = null
) {
    // 优先从本地获取，远程作为备份
}
```

### 测试策略

#### 单元测试

```kotlin
@Test
fun `calculateHealthScore returns 100 when no expired items`() {
    val stats = OverallStatistics(
        totalIngredients = 10,
        expiredCount = 0,
        // ...
    )
    
    val score = StatisticsUtils.calculateHealthScore(stats)
    
    assertEquals(100, score)
}
```

#### ViewModel 测试

```kotlin
@ExperimentalCoroutinesApi
class StatisticsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    
    private lateinit var viewModel: StatisticsViewModel
    private lateinit var repository: FakeIngredientRepository
    
    @Before
    fun setup() {
        repository = FakeIngredientRepository()
        viewModel = StatisticsViewModel(repository)
    }
    
    @Test
    fun `loadStatistics updates categoryStats`() = runTest {
        // Given
        repository.setCategoryStats(testData)
        
        // When
        viewModel.loadStatistics()
        
        // Then
        assertEquals(testData, viewModel.categoryStats.value)
    }
}
```

---

## 📝 Git 工作流

### 分支策略

```
main          生产分支，保持稳定
  │
  ├── develop    开发分支，日常开发
  │   ├── feature/statistics    功能分支
  │   ├── feature/notifications
  │   └── bugfix/expiry-calculation
  │
  └── hotfix     紧急修复分支
```

### 提交规范

```
<type>(<scope>): <subject>

<body>

<footer>
```

类型：
- `feat`: 新功能
- `fix`: 修复
- `docs`: 文档
- `style`: 格式调整
- `refactor`: 重构
- `test`: 测试
- `chore`: 构建/工具

示例：
```
feat(statistics): add monthly trend chart

- Add BarChart for monthly ingredient addition trends
- Implement data aggregation in Repository
- Add corresponding ViewModel methods

Closes #123
```

---

## 🔧 常用命令

```bash
# 构建项目
./gradlew build

# 运行测试
./gradlew test

# 运行 Android 测试
./gradlew connectedAndroidTest

# 打包 APK
./gradlew assembleDebug
./gradlew assembleRelease

# 清理构建
./gradlew clean

# 查看依赖树
./gradlew dependencies

# 检查代码质量
./gradlew ktlintCheck
./gradlew detekt
```

---

## 📚 参考资源

- [Kotlin 官方文档](https://kotlinlang.org/docs/)
- [Android 开发者指南](https://developer.android.com/guide)
- [MVVM 架构指南](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Room 数据库](https://developer.android.com/training/data-storage/room)
- [Kotlin 协程](https://kotlinlang.org/docs/coroutines-overview.html)
