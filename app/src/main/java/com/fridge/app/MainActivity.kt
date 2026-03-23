package com.fridge.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.fridge.app.ui.adapter.ViewPagerAdapter
import com.fridge.app.ui.fragment.IngredientListFragment
import com.fridge.app.ui.fragment.RecipeListFragment
import com.fridge.app.ui.fragment.StatisticsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var fabAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupViewPager()
        setupFab()
    }

    private fun initViews() {
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        fabAdd = findViewById(R.id.fabAdd)
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(this)

        // 添加食材列表页面
        adapter.addFragment(IngredientListFragment(), getString(R.string.tab_ingredients))
        // 添加菜谱列表页面
        adapter.addFragment(RecipeListFragment(), getString(R.string.tab_recipes))
        // 添加统计页面
        adapter.addFragment(StatisticsFragment(), getString(R.string.tab_statistics))

        viewPager.adapter = adapter

        // 关联 TabLayout 和 ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.ic_fridge)
                    tab.text = getString(R.string.tab_ingredients)
                }
                1 -> {
                    tab.setIcon(R.drawable.ic_recipe)
                    tab.text = getString(R.string.tab_recipes)
                }
                2 -> {
                    tab.setIcon(R.drawable.ic_chart)
                    tab.text = getString(R.string.tab_statistics)
                }
            }
        }.attach()

        // 监听页面切换
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateFabForPage(position)
            }
        })
    }

    private fun setupFab() {
        fabAdd.setOnClickListener {
            when (viewPager.currentItem) {
                0 -> {
                    // 在食材页面，添加食材
                    (supportFragmentManager.findFragmentByTag("f0") as? IngredientListFragment)
                        ?.showAddIngredientDialog()
                }
                1 -> {
                    // 在菜谱页面，添加菜谱（暂未实现）
                    // (supportFragmentManager.findFragmentByTag("f1") as? RecipeListFragment)
                    //     ?.showAddRecipeDialog()
                }
            }
        }
    }

    private fun updateFabForPage(position: Int) {
        when (position) {
            0 -> {
                fabAdd.show()
                fabAdd.setImageResource(R.drawable.ic_add)
                fabAdd.contentDescription = getString(R.string.add_ingredient)
            }
            1 -> {
                fabAdd.show()
                fabAdd.setImageResource(R.drawable.ic_add)
                fabAdd.contentDescription = getString(R.string.add_recipe)
            }
            2 -> {
                // 统计页面不需要FAB，隐藏
                fabAdd.hide()
            }
        }
    }
    
    companion object {
        const val EXTRA_TAB_INDEX = "tab_index"
    }
}