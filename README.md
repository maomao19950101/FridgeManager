# 🧊 冰箱管家 (FridgeManager)

一款智能的冰箱食材管理 Android 应用，帮助您轻松追踪食材库存、管理过期提醒、发现可制作的菜谱。

## ✨ 功能介绍

### 📦 食材管理
- **添加食材**：记录食材名称、分类、数量、保质期等信息
- **拍照记录**：为食材拍照，方便快速识别
- **分类管理**：支持蔬菜、水果、肉类、海鲜、乳制品等11种分类
- **智能搜索**：快速搜索冰箱中的食材
- **过期提醒**：自动提醒即将过期和已过期的食材

### 📊 统计图表
- **分类占比**：饼图展示各类食材占比
- **状态分布**：环形图显示正常、警告、即将过期、已过期的食材分布
- **月度趋势**：柱状图展示每月新增和过期食材趋势
- **过期率趋势**：折线图追踪食材过期率变化
- **健康评分**：综合评估冰箱健康状态

### 📖 菜谱推荐
- **智能匹配**：根据现有食材推荐可制作的菜谱
- **难度分级**：简单、中等、困难三级难度
- **详细步骤**：提供完整的烹饪步骤和食材清单
- **收藏功能**：收藏喜欢的菜谱

### 🔔 通知提醒
- **过期提醒**：食材过期时推送通知
- **即将过期**：提前1天和3天提醒
- **定期检查**：后台自动检查食材状态

## 📱 截图预览

### 食材列表页面
- 展示所有食材卡片，显示名称、分类、剩余天数
- 支持搜索和分类筛选
- 下拉刷新数据

### 食材详情页面
- 展示食材完整信息
- 支持编辑和删除
- 可查看和更换食材照片

### 统计页面
- 卡片式布局展示各类统计图表
- 支持下拉刷新
- 实时计算健康评分

### 菜谱页面
- 展示可制作的菜谱
- 标记当前可制作的菜谱
- 显示所需食材和烹饪时间

## 🛠 技术栈

### 架构
- **MVVM 架构**：Model-View-ViewModel 分离关注点
- **Repository 模式**：数据层抽象，支持多数据源
- **单例模式**：数据库和 Repository 管理

### UI
- **Material Design 3**：遵循 Material You 设计规范
- **ViewBinding**：类型安全的视图绑定
- **ViewPager2 + TabLayout**：页面切换和导航

### 数据存储
- **Room**：SQLite 数据库的抽象层
- **DataStore**：偏好设置存储
- **协程**：异步数据库操作

### 图表
- **MPAndroidChart**：强大的图表库
  - 饼图：食材分类占比
  - 柱状图：月度趋势
  - 环形图：状态分布
  - 折线图：过期率趋势

### 图片处理
- **PhotoPicker**：系统照片选择器
- **Glide**：图片加载和缓存

### 后台任务
- **WorkManager**：定期检查食材过期状态
- **Notification**：本地通知提醒

### 依赖注入
- 手动依赖注入（轻量级实现）

## 📥 安装使用

### 环境要求
- Android 8.0 (API 26) 及以上
- 支持架构：arm64-v8a, armeabi-v7a, x86, x86_64

### 从源码构建

1. 克隆仓库
```bash
git clone https://github.com/yourusername/FridgeManager.git
cd FridgeManager
```

2. 使用 Android Studio 打开项目

3. 同步 Gradle 并构建
```bash
./gradlew build
```

4. 运行到设备或模拟器
```bash
./gradlew installDebug
```

### 使用说明

1. **添加食材**
   - 点击首页右下角「+」按钮
   - 填写食材信息（名称、分类、数量、保质期）
   - 可选：为食材拍照
   - 点击保存

2. **查看统计**
   - 切换到「统计」标签页
   - 查看各类图表数据
   - 下拉刷新最新统计

3. **发现菜谱**
   - 切换到「菜谱」标签页
   - 查看根据现有食材推荐的菜谱
   - 点击菜谱查看详细做法

4. **管理提醒**
   - 应用会自动在后台检查食材状态
   - 有过期食材时会推送通知
   - 可在系统设置中管理通知权限

## 📁 项目结构

```
FridgeManager/
├── app/
│   ├── src/main/java/com/fridge/app/
│   │   ├── data/
│   │   │   ├── dao/              # Room DAO 接口
│   │   │   ├── db/               # 数据库定义
│   │   │   ├── local/            # 本地数据源
│   │   │   ├── model/            # 数据模型
│   │   │   └── repository/       # 仓库层
│   │   ├── ui/
│   │   │   ├── activity/         # Activity
│   │   │   ├── adapter/          # RecyclerView 适配器
│   │   │   ├── fragment/         # Fragment
│   │   │   └── viewmodel/        # ViewModel
│   │   ├── utils/                # 工具类
│   │   ├── worker/               # WorkManager 任务
│   │   ├── MainActivity.kt       # 主Activity
│   │   └── FridgeApplication.kt  # Application
│   ├── src/main/res/
│   │   ├── drawable/             # 图形资源
│   │   ├── layout/               # 布局文件
│   │   ├── values/               # 字符串、颜色、样式
│   │   └── xml/                  # 配置文件
│   └── build.gradle              # 模块构建配置
├── docs/                         # 文档
├── build.gradle                  # 项目构建配置
├── settings.gradle               # 项目设置
└── README.md                     # 项目说明
```

## 🔧 核心模块说明

### 数据层 (data)
- **Ingredient.kt** - 食材数据模型
- **Recipe.kt** - 菜谱数据模型
- **Statistics.kt** - 统计数据模型
- **IngredientDao.kt** - 食材数据库访问
- **IngredientRepository.kt** - 食材数据仓库

### UI 层 (ui)
- **MainActivity.kt** - 主界面，管理 Tab 导航
- **IngredientListFragment.kt** - 食材列表
- **RecipeListFragment.kt** - 菜谱列表
- **StatisticsFragment.kt** - 统计图表
- **IngredientDetailActivity.kt** - 食材详情

### 统计模块 (utils)
- **StatisticsUtils.kt** - 图表数据转换、健康评分计算

### 后台任务 (worker)
- **ExpiryCheckWorker.kt** - 定期检查过期食材
- **BootCompletedReceiver.kt** - 开机重启任务

## 📝 更新日志

详见 [CHANGELOG.md](CHANGELOG.md)

## 📄 许可证

```
Copyright 2024 FridgeManager

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

## 📧 联系方式

如有问题或建议，请通过以下方式联系：
- 邮箱: your.email@example.com
- GitHub Issues: https://github.com/yourusername/FridgeManager/issues

---

**冰箱管家** - 让食材管理更智能 🧊
