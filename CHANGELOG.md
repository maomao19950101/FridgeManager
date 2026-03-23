# 更新日志

所有 notable 的更改都将记录在此文件中。

格式基于 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.0.0/)，
并且本项目遵循 [语义化版本](https://semver.org/lang/zh-CN/)。

---

## [Unreleased]

### 计划中
- [ ] 云端同步功能
- [ ] 扫码录入食材
- [ ] 食材营养信息
- [ ] 购物清单生成
- [ ] 多语言支持
- [ ] 暗黑模式

---

## [1.1.0] - 2024-03-22

### ✨ 新增功能

#### 统计图表功能
- **食材分类占比饼图**：直观展示各类食材的比例分布
- **食材状态分布环形图**：显示正常、注意、即将过期、已过期的食材分布
- **月度新增趋势柱状图**：追踪每月新增和过期食材数量
- **过期率趋势折线图**：监控食材过期率的变化趋势
- **健康度评分系统**：综合评估冰箱健康状态（0-100分）
- **下拉刷新**：支持下拉刷新统计数据

#### 新增文件
- `data/model/Statistics.kt` - 统计数据模型定义
- `ui/viewmodel/StatisticsViewModel.kt` - 统计ViewModel
- `ui/fragment/StatisticsFragment.kt` - 统计页面Fragment
- `utils/StatisticsUtils.kt` - 统计工具类
- `fragment_statistics.xml` - 统计页面布局
- `ic_chart.xml` - 图表图标

#### DAO 扩展
- 按分类统计数量查询
- 按状态统计数量查询
- 按月统计新增趋势
- 过期率统计查询

#### Repository 扩展
- 总体统计信息获取
- 分类统计信息获取
- 状态统计信息获取
- 月度趋势数据获取
- 过期率趋势数据获取
- 完整统计报表获取

#### UI 更新
- MainActivity 添加统计Tab
- activity_main.xml 添加统计TabItem
- 统计页面隐藏FAB按钮

### 🛠 技术改进
- 使用 MPAndroidChart 库实现图表功能
- 优化数据查询性能
- 添加统计数据缓存

### 📝 文档
- 添加完整 API 文档
- 添加开发文档
- 更新 README.md

---

## [1.0.0] - 2024-03-20

### ✨ 功能列表

#### 🥗 食材管理
- **添加食材**：记录食材名称、分类、数量、保质期等信息
- **编辑食材**：修改已添加的食材信息
- **删除食材**：移除不再需要的食材记录
- **拍照记录**：为食材拍照，方便快速识别
- **分类管理**：支持11种食材分类：
  - 蔬菜、水果、肉类、海鲜
  - 蛋类、乳制品、米面
  - 调料、罐头、冷冻食品、其他
- **智能搜索**：通过名称快速搜索食材
- **分类筛选**：按分类筛选查看食材

#### 📊 数据统计
- 食材总数统计
- 即将过期食材数量
- 已过期食材数量
- 按状态分类统计（界面基础版）

#### 📖 菜谱推荐
- **智能匹配**：根据现有食材推荐可制作的菜谱
- **难度分级**：简单、中等、困难三级难度
- **详细步骤**：提供完整的烹饪步骤
- **收藏功能**：收藏喜欢的菜谱
- **分享功能**：分享菜谱信息

#### 🔔 通知提醒
- **过期提醒**：食材过期时推送通知
- **即将过期提醒**：提前1天提醒
- **警告提醒**：提前3天提醒
- **定期检查**：后台自动检查食材状态
- **通知渠道**：分类管理通知类型

#### 🖼️ 图片功能
- **照片选择**：从相册选择食材照片
- **拍照录入**：直接拍摄食材照片
- **图片预览**：全屏查看食材照片
- **图片旋转**：支持旋转查看
- **图片分享**：分享食材照片

#### 🎨 UI 设计
- **Material Design 3**：遵循 Material You 设计规范
- **Tab 导航**：食材、菜谱双标签页
- **卡片式布局**：清晰的食材信息展示
- **颜色标识**：不同状态用不同颜色表示
  - 🟢 正常（绿色）
  - 🟡 注意（橙色）
  - 🟠 即将过期（深橙）
  - 🔴 已过期（红色）
- **空状态提示**：无数据时友好提示
- **加载动画**：数据加载时显示进度

### 🏗️ 技术架构

#### 架构模式
- **MVVM 架构**：Model-View-ViewModel 分离关注点
- **Repository 模式**：数据层抽象，支持多数据源
- **单例模式**：数据库和 Repository 管理

#### UI 技术
- **ViewBinding**：类型安全的视图绑定
- **ViewPager2 + TabLayout**：页面切换和导航
- **RecyclerView**：列表展示
- **Material Components**：Material Design 组件

#### 数据存储
- **Room**：SQLite 数据库的抽象层
- **DataStore**：偏好设置存储
- **协程**：异步数据库操作

#### 后台任务
- **WorkManager**：定期检查食材过期状态
- **NotificationManager**：本地通知提醒
- **BroadcastReceiver**：开机重启任务

### 📁 项目结构

```
app/src/main/java/com/fridge/app/
├── data/
│   ├── dao/              # IngredientDao, RecipeDao
│   ├── db/               # AppDatabase
│   ├── local/            # Converters
│   ├── model/            # Ingredient, Recipe
│   └── repository/       # IngredientRepository, RecipeRepository
├── ui/
│   ├── activity/         # ImagePreview, IngredientDetail, RecipeDetail
│   ├── adapter/          # IngredientAdapter, RecipeAdapter, ViewPagerAdapter
│   ├── fragment/         # IngredientListFragment, RecipeListFragment
│   └── viewmodel/        # IngredientViewModel, RecipeViewModel
├── utils/                # ImageUtils, PhotoPicker
├── worker/               # ExpiryCheckWorker, BootCompletedReceiver
├── MainActivity.kt
└── FridgeApplication.kt
```

### 📱 系统要求
- Android 8.0 (API 26) 及以上
- 支持架构：arm64-v8a, armeabi-v7a, x86, x86_64

### 📦 依赖库
- AndroidX Core / AppCompat
- Material Components
- Room
- ViewModel / LiveData
- WorkManager
- Navigation Component
- MPAndroidChart (v1.1.0新增)
- Glide

### 🎉 初始版本
这是冰箱管家应用的初始版本，包含完整的食材管理和菜谱推荐功能。

---

## 版本对比

| 功能 | v1.0.0 | v1.1.0 |
|------|--------|--------|
| 食材管理 | ✅ | ✅ |
| 菜谱推荐 | ✅ | ✅ |
| 过期提醒 | ✅ | ✅ |
| 基础统计 | ✅ | ✅ |
| **分类占比饼图** | ❌ | ✅ |
| **状态分布环形图** | ❌ | ✅ |
| **月度趋势柱状图** | ❌ | ✅ |
| **过期率趋势折线图** | ❌ | ✅ |
| **健康度评分** | ❌ | ✅ |
| **完整文档** | ❌ | ✅ |

---

## 贡献者

感谢所有为冰箱管家做出贡献的开发者！

---

[Unreleased]: https://github.com/yourusername/FridgeManager/compare/v1.1.0...HEAD
[1.1.0]: https://github.com/yourusername/FridgeManager/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/yourusername/FridgeManager/releases/tag/v1.0.0
