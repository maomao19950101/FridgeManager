# API 文档

本文档详细说明冰箱管家应用的数据模型、Repository 接口和 ViewModel 使用方法。

## 📦 数据模型

### Ingredient (食材)

食材是应用的核心数据实体，记录冰箱中存储的食物信息。

```kotlin
@Entity(tableName = "ingredients")
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,                              // 唯一标识
    val name: String,                              // 食材名称
    val category: IngredientCategory,              // 食材分类
    val quantity: Float,                           // 数量
    val unit: String,                              // 单位（个、克、升等）
    val addDate: Date,                             // 添加日期
    val expireDate: Date? = null,                  // 过期日期（可选）
    val photoUri: String? = null,                  // 照片URI（可选）
    val notes: String? = null                      // 备注（可选）
)
```

#### 方法

| 方法 | 返回值 | 说明 |
|------|--------|------|
| `getDaysUntilExpire()` | `Int?` | 获取距离过期的天数，返回 null 表示无过期日期 |
| `getStatus()` | `IngredientStatus` | 获取食材当前状态 |

#### 示例

```kotlin
val ingredient = Ingredient(
    name = "西红柿",
    category = IngredientCategory.VEGETABLE,
    quantity = 3f,
    unit = "个",
    addDate = Date(),
    expireDate = Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000) // 7天后过期
)

val daysLeft = ingredient.getDaysUntilExpire()  // 7
val status = ingredient.getStatus()             // IngredientStatus.NORMAL
```

---

### Recipe (菜谱)

菜谱实体包含烹饪所需的食材清单和步骤。

```kotlin
@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,                              // 菜谱名称
    val description: String?,                      // 描述
    val imageUrl: String?,                         // 图片URL
    val ingredients: List<RecipeIngredient>,       // 所需食材清单
    val steps: List<String>,                       // 烹饪步骤
    val cookingTime: Int,                          // 烹饪时间（分钟）
    val difficulty: DifficultyLevel,               // 难度等级
    val servings: Int,                             // 份量
    val tags: List<String> = emptyList(),          // 标签
    val isFavorite: Boolean = false                // 是否收藏
)

// 菜谱所需食材
@Entity
data class RecipeIngredient(
    val ingredientId: Long,                        // 对应食材ID（可选）
    val name: String,                              // 食材名称
    val quantity: Float,                           // 所需数量
    val unit: String,                              // 单位
    val isOptional: Boolean = false                // 是否可选
)
```

---

### Statistics (统计)

统计数据模型用于图表展示。

#### OverallStatistics (总体统计)

```kotlin
data class OverallStatistics(
    val totalIngredients: Int,              // 总食材数
    val expiredCount: Int,                  // 已过期数量
    val expiringSoonCount: Int,             // 即将过期数量（1天内）
    val warningCount: Int,                  // 警告数量（3天内）
    val normalCount: Int,                   // 正常数量
    val expiredRate: Float,                 // 过期率（0-100）
    val categoryCount: Int                  // 使用的分类数量
)
```

#### CategoryStatistics (分类统计)

```kotlin
data class CategoryStatistics(
    val category: IngredientCategory,       // 食材分类
    val categoryName: String,               // 分类显示名称
    val count: Int,                         // 该分类食材数量
    val percentage: Float,                  // 占比（0-100）
    val color: Int                          // 图表颜色
)
```

#### StatusStatistics (状态统计)

```kotlin
data class StatusStatistics(
    val status: IngredientStatus,           // 食材状态
    val statusName: String,                 // 状态显示名称
    val count: Int,                         // 该状态食材数量
    val percentage: Float,                  // 占比（0-100）
    val color: Int                          // 图表颜色
)
```

#### MonthlyTrend (月度趋势)

```kotlin
data class MonthlyTrend(
    val yearMonth: String,                  // 年月（格式：yyyy-MM）
    val monthLabel: String,                 // 显示标签（如"1月"）
    val addedCount: Int,                    // 新增食材数
    val expiredCount: Int                   // 过期食材数
)
```

#### ExpiryRateTrend (过期率趋势)

```kotlin
data class ExpiryRateTrend(
    val yearMonth: String,                  // 年月
    val monthLabel: String,                 // 显示标签
    val expiryRate: Float,                  // 过期率（0-100）
    val totalCount: Int,                    // 当月累计总数
    val expiredCount: Int                   // 当月累计过期数
)
```

#### StatisticsReport (完整报表)

```kotlin
data class StatisticsReport(
    val overall: OverallStatistics,                                 // 总体统计
    val categoryStats: List<CategoryStatistics>,                    // 分类统计列表
    val statusStats: List<StatusStatistics>,                        // 状态统计列表
    val monthlyTrends: List<MonthlyTrend>,                          // 月度趋势
    val expiryRateTrends: List<ExpiryRateTrend>,                    // 过期率趋势
    val generatedAt: Long = System.currentTimeMillis()              // 生成时间戳
)
```

---

## 🗄️ Repository 接口

### IngredientRepository

食材数据仓库，提供对食材数据的 CRUD 操作和统计查询。

#### 基础 CRUD

```kotlin
class IngredientRepository(private val ingredientDao: IngredientDao) {
    // LiveData 形式获取所有食材
    val allIngredients: LiveData<List<Ingredient>>
    
    // 根据ID获取食材
    suspend fun getIngredientById(id: Long): Ingredient?
    
    // 插入食材，返回新ID
    suspend fun insertIngredient(ingredient: Ingredient): Long
    
    // 更新食材
    suspend fun updateIngredient(ingredient: Ingredient)
    
    // 删除食材
    suspend fun deleteIngredient(ingredient: Ingredient)
    suspend fun deleteIngredientById(id: Long)
    
    // 删除所有食材
    suspend fun deleteAllIngredients()
}
```

#### 查询方法

```kotlin
// 按分类获取食材
fun getIngredientsByCategory(category: IngredientCategory): LiveData<List<Ingredient>>

// 获取有效食材（未过期）
fun getValidIngredients(): LiveData<List<Ingredient>>

// 获取已过期食材
fun getExpiredIngredients(): LiveData<List<Ingredient>>

// 获取指定天数内将过期的食材
suspend fun getExpiringIngredients(days: Int): List<Ingredient>

// 搜索食材
fun searchIngredients(query: String): LiveData<List<Ingredient>>

// 获取食材总数
suspend fun getIngredientCount(): Int

// 获取即将过期数量
suspend fun getExpiringCount(days: Int): Int

// 获取即将过期数量（3天内）
suspend fun getExpiringSoonCount(): Int
```

#### 统计方法

```kotlin
// 获取总体统计
suspend fun getOverallStatistics(): OverallStatistics

// 获取分类统计
suspend fun getCategoryStatistics(): List<CategoryStatistics>

// 获取状态统计
suspend fun getStatusStatistics(): List<StatusStatistics>

// 获取月度趋势（默认最近12个月）
suspend fun getMonthlyTrends(months: Int = 12): List<MonthlyTrend>

// 获取过期率趋势
suspend fun getExpiryRateTrends(months: Int = 12): List<ExpiryRateTrend>

// 获取完整统计报表
suspend fun getStatisticsReport(): StatisticsReport
```

#### 使用示例

```kotlin
class MyViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: IngredientRepository
    
    init {
        val dao = AppDatabase.getDatabase(application).ingredientDao()
        repository = IngredientRepository(dao)
    }
    
    fun loadStatistics() = viewModelScope.launch {
        val report = repository.getStatisticsReport()
        // 处理统计报表
    }
    
    fun addIngredient(ingredient: Ingredient) = viewModelScope.launch {
        val id = repository.insertIngredient(ingredient)
        // 食材已添加
    }
}
```

---

### RecipeRepository

菜谱数据仓库，提供菜谱的查询和匹配功能。

```kotlin
class RecipeRepository(private val recipeDao: RecipeDao) {
    // LiveData 形式获取所有菜谱
    val allRecipes: LiveData<List<Recipe>>
    
    // 根据ID获取菜谱
    suspend fun getRecipeById(id: Long): Recipe?
    
    // 搜索菜谱
    fun searchRecipes(query: String): LiveData<List<Recipe>>
    
    // 根据可用食材获取可制作的菜谱
    fun getRecipesByAvailableIngredients(ingredients: List<Ingredient>): List<RecipeWithAvailability>
    
    // 获取收藏的菜谱
    fun getFavoriteRecipes(): LiveData<List<Recipe>>
    
    // 切换收藏状态
    suspend fun toggleFavorite(recipeId: Long)
    
    // 插入菜谱
    suspend fun insertRecipe(recipe: Recipe): Long
    
    // 更新菜谱
    suspend fun updateRecipe(recipe: Recipe)
    
    // 删除菜谱
    suspend fun deleteRecipe(recipe: Recipe)
}
```

#### RecipeWithAvailability

```kotlin
data class RecipeWithAvailability(
    val recipe: Recipe,
    val canMake: Boolean,                      // 是否可以制作
    val missingIngredients: List<RecipeIngredient>, // 缺少的食材
    val optionalMissing: List<RecipeIngredient>     // 缺少的可选食材
)
```

---

## 🎯 ViewModel 使用说明

### IngredientViewModel

管理食材相关的 UI 数据和业务逻辑。

```kotlin
class IngredientViewModel(application: Application) : AndroidViewModel(application) {
    // 所有食材（LiveData）
    val allIngredients: LiveData<List<Ingredient>>
    
    // 搜索结果
    val searchResults: LiveData<List<Ingredient>>
    
    // 统计数量
    val expiringCount: LiveData<Int>
    val expiredCount: LiveData<Int>
    
    // CRUD 操作
    fun insert(ingredient: Ingredient)
    fun update(ingredient: Ingredient)
    fun delete(ingredient: Ingredient)
    fun deleteById(id: Long)
    
    // 查询
    fun getIngredientById(id: Long): LiveData<Ingredient?>
    fun getIngredientsByCategory(category: IngredientCategory): LiveData<List<Ingredient>>
    fun searchIngredients(query: String): LiveData<List<Ingredient>>
    
    // 过期相关
    fun getExpiredIngredients(): LiveData<List<Ingredient>>
    fun getExpiringSoonIngredients(): LiveData<List<Ingredient>>
    
    // 获取状态统计
    fun getStatusStats(ingredients: List<Ingredient>): Map<IngredientStatus, Int>
    
    // 清空所有
    fun deleteAll()
}
```

#### 在 Activity/Fragment 中使用

```kotlin
class IngredientListFragment : Fragment() {
    private val viewModel: IngredientViewModel by viewModels()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // 观察食材列表
        viewModel.allIngredients.observe(viewLifecycleOwner) { ingredients ->
            adapter.submitList(ingredients)
        }
        
        // 添加食材
        val newIngredient = Ingredient(
            name = "胡萝卜",
            category = IngredientCategory.VEGETABLE,
            quantity = 2f,
            unit = "根",
            addDate = Date()
        )
        viewModel.insert(newIngredient)
    }
}
```

---

### RecipeViewModel

管理菜谱相关的 UI 数据。

```kotlin
class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    // 所有菜谱
    val allRecipes: LiveData<List<Recipe>>
    
    // 可制作的菜谱（根据现有食材）
    val availableRecipes: LiveData<List<RecipeWithAvailability>>
    
    // 收藏的菜谱
    val favoriteRecipes: LiveData<List<Recipe>>
    
    // 搜索
    val searchResults: LiveData<List<Recipe>>
    
    // 操作
    fun searchRecipes(query: String)
    fun toggleFavorite(recipeId: Long)
    fun refreshAvailableRecipes(ingredients: List<Ingredient>)
    fun insert(recipe: Recipe)
    fun update(recipe: Recipe)
    fun delete(recipe: Recipe)
}
```

---

### StatisticsViewModel

管理统计相关的数据和图表。

```kotlin
class StatisticsViewModel(application: Application) : AndroidViewModel(application) {
    // 统计数据
    val overallStats: LiveData<OverallStatistics>
    val categoryStats: LiveData<List<CategoryStatistics>>
    val statusStats: LiveData<List<StatusStatistics>>
    val monthlyTrends: LiveData<List<MonthlyTrend>>
    val expiryRateTrends: LiveData<List<ExpiryRateTrend>>
    val statisticsReport: LiveData<StatisticsReport>
    
    // 状态
    val isLoading: LiveData<Boolean>
    val error: LiveData<String?>
    
    // 操作
    fun loadAllStatistics()                          // 加载所有统计
    fun loadStatisticsReport()                       // 加载完整报表
    fun refreshStatistics()                          // 刷新数据
    fun loadMonthlyTrends(months: Int)               // 加载指定月数趋势
    fun loadExpiryRateTrends(months: Int)            // 加载过期率趋势
    fun clearError()                                 // 清除错误
}
```

#### 使用示例

```kotlin
class StatisticsFragment : Fragment() {
    private val viewModel: StatisticsViewModel by viewModels()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // 观察总体统计
        viewModel.overallStats.observe(viewLifecycleOwner) { stats ->
            binding.tvTotalCount.text = stats.totalIngredients.toString()
            binding.tvExpiredRate.text = "${String.format("%.1f", stats.expiredRate)}%"
        }
        
        // 观察分类统计用于饼图
        viewModel.categoryStats.observe(viewLifecycleOwner) { stats ->
            val pieData = StatisticsUtils.createPieData(stats)
            binding.pieChart.data = pieData
            binding.pieChart.invalidate()
        }
        
        // 观察加载状态
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = 
                if (isLoading) View.VISIBLE else View.GONE
        }
        
        // 观察错误
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                viewModel.clearError()
            }
        }
        
        // 下拉刷新
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshStatistics()
            binding.swipeRefresh.isRefreshing = false
        }
    }
    
    override fun onResume() {
        super.onResume()
        viewModel.refreshStatistics()
    }
}
```

---

## 🛠️ 工具类

### StatisticsUtils

统计工具类，提供图表数据转换和健康评分计算。

```kotlin
object StatisticsUtils {
    // 图表数据转换
    fun createPieData(categoryStats: List<CategoryStatistics>): PieData
    fun createStatusPieData(statusStats: List<StatusStatistics>): PieData
    fun createBarData(monthlyTrends: List<MonthlyTrend>): BarData
    fun createLineData(expiryRateTrends: List<ExpiryRateTrend>): LineData
    
    // 工具方法
    fun formatPercentage(percentage: Float): String
    fun formatCount(count: Int): String
    fun calculateExpiryRate(total: Int, expired: Int): Float
    fun calculateHealthScore(overallStats: OverallStatistics): Int
    fun getHealthRating(score: Int): String
    fun getHealthColor(score: Int): Int
    fun getMonthLabels(monthlyTrends: List<MonthlyTrend>): List<String>
    fun generateSummary(overallStats: OverallStatistics): String
}
```

#### 使用示例

```kotlin
// 创建饼图数据
val pieData = StatisticsUtils.createPieData(categoryStats)
pieChart.data = pieData

// 计算健康度
val healthScore = StatisticsUtils.calculateHealthScore(overallStats)
val rating = StatisticsUtils.getHealthRating(healthScore)
val color = StatisticsUtils.getHealthColor(healthScore)

// 生成摘要文本
val summary = StatisticsUtils.generateSummary(overallStats)
```

---

## 📊 枚举类型

### IngredientCategory (食材分类)

```kotlin
enum class IngredientCategory {
    VEGETABLE,      // 蔬菜
    FRUIT,          // 水果
    MEAT,           // 肉类
    SEAFOOD,        // 海鲜
    EGG,            // 蛋
    DAIRY,          // 乳制品
    GRAIN,          // 米面
    SEASONING,      // 调料
    CANNED,         // 罐头
    FROZEN,         // 冷冻食品
    OTHER           // 其他
}
```

**扩展方法：**
- `getDisplayName(): String` - 获取中文名称
- `getChartColor(): Int` - 获取图表颜色

### IngredientStatus (食材状态)

```kotlin
enum class IngredientStatus {
    NORMAL,         // 正常（超过3天过期）
    WARNING,        // 注意（3天内过期）
    EXPIRING_SOON,  // 即将过期（1天内）
    EXPIRED         // 已过期
}
```

**扩展方法：**
- `getDisplayName(): String` - 获取中文名称
- `getColorRes(): Int` - 获取颜色资源ID

---

## 🔗 相关文档

- [开发文档](DEVELOPMENT.md) - 开发环境搭建和代码规范
- [CHANGELOG](../CHANGELOG.md) - 版本更新日志
