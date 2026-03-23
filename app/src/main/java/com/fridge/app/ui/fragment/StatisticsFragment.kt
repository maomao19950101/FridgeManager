package com.fridge.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fridge.app.R
import com.fridge.app.databinding.FragmentStatisticsBinding
import com.fridge.app.ui.viewmodel.StatisticsViewModel
import com.fridge.app.utils.StatisticsUtils
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter

class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StatisticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCharts()
        setupObservers()
        setupRefresh()
    }

    private fun setupCharts() {
        setupPieChart()
        setupBarChart()
        setupDoughnutChart()
        setupLineChart()
    }

    /**
     * 配置饼图（分类占比）
     */
    private fun setupPieChart() {
        binding.pieChartCategory.apply {
            description.isEnabled = false
            isRotationEnabled = true
            isHighlightPerTapEnabled = true
            dragDecelerationFrictionCoef = 0.95f
            
            legend.apply {
                isEnabled = true
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                orientation = Legend.LegendOrientation.VERTICAL
                setDrawInside(false)
                textSize = 10f
            }
            
            setEntryLabelColor(android.graphics.Color.BLACK)
            setEntryLabelTextSize(10f)
            setUsePercentValues(true)
            setExtraOffsets(5f, 5f, 5f, 5f)
            
            animateY(1000, Easing.EaseInOutQuad)
        }
    }

    /**
     * 配置柱状图（月度趋势）
     */
    private fun setupBarChart() {
        binding.barChartMonthly.apply {
            description.isEnabled = false
            setDrawGridBackground(false)
            setPinchZoom(false)
            
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f
                labelCount = 6
                textSize = 10f
            }
            
            axisLeft.apply {
                setDrawGridLines(true)
                axisMinimum = 0f
                textSize = 10f
            }
            
            axisRight.isEnabled = false
            
            legend.apply {
                isEnabled = true
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
                textSize = 10f
            }
            
            animateY(1000, Easing.EaseInOutQuad)
        }
    }

    /**
     * 配置环形图（状态分布）
     */
    private fun setupDoughnutChart() {
        binding.doughnutChartStatus.apply {
            description.isEnabled = false
            isRotationEnabled = true
            dragDecelerationFrictionCoef = 0.95f
            
            // 设置环形图属性
            isDrawHoleEnabled = true
            holeRadius = 45f
            transparentCircleRadius = 50f
            setHoleColor(android.graphics.Color.WHITE)
            setTransparentCircleColor(android.graphics.Color.WHITE)
            setTransparentCircleAlpha(110)
            
            legend.apply {
                isEnabled = true
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                orientation = Legend.LegendOrientation.VERTICAL
                setDrawInside(false)
                textSize = 10f
            }
            
            setEntryLabelColor(android.graphics.Color.BLACK)
            setEntryLabelTextSize(10f)
            setCenterTextSize(14f)
            setExtraOffsets(5f, 5f, 5f, 5f)
            
            animateY(1000, Easing.EaseInOutQuad)
        }
    }

    /**
     * 配置折线图（过期率趋势）
     */
    private fun setupLineChart() {
        binding.lineChartExpiryRate.apply {
            description.isEnabled = false
            setDrawGridBackground(false)
            setPinchZoom(true)
            
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f
                labelCount = 6
                textSize = 10f
            }
            
            axisLeft.apply {
                setDrawGridLines(true)
                axisMinimum = 0f
                axisMaximum = 100f
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return "${value.toInt()}%"
                    }
                }
                textSize = 10f
            }
            
            axisRight.isEnabled = false
            
            legend.apply {
                isEnabled = true
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
                textSize = 10f
            }
            
            animateX(1000, Easing.EaseInOutQuad)
        }
    }

    private fun setupObservers() {
        // 观察总体统计
        viewModel.overallStats.observe(viewLifecycleOwner) { stats ->
            binding.tvTotalCount.text = stats.totalIngredients.toString()
            binding.tvExpiredCount.text = stats.expiredCount.toString()
            binding.tvExpiringSoonCount.text = stats.expiringSoonCount.toString()
            binding.tvWarningCount.text = stats.warningCount.toString()
            
            // 计算并显示健康度评分
            val healthScore = StatisticsUtils.calculateHealthScore(stats)
            binding.tvHealthScore.text = healthScore.toString()
            binding.tvHealthScore.setTextColor(StatisticsUtils.getHealthColor(healthScore))
            binding.tvHealthRating.text = StatisticsUtils.getHealthRating(healthScore)
            
            // 显示过期率
            binding.tvExpiredRate.text = StatisticsUtils.formatPercentage(stats.expiredRate)
        }

        // 观察分类统计
        viewModel.categoryStats.observe(viewLifecycleOwner) { stats ->
            if (stats.isNotEmpty()) {
                val pieData = StatisticsUtils.createPieData(stats)
                binding.pieChartCategory.data = pieData
                binding.pieChartCategory.invalidate()
                binding.pieChartCategory.animateY(1000)
            }
        }

        // 观察月度趋势
        viewModel.monthlyTrends.observe(viewLifecycleOwner) { trends ->
            if (trends.isNotEmpty()) {
                val barData = StatisticsUtils.createBarData(trends)
                binding.barChartMonthly.data = barData
                
                // 设置X轴标签
                val labels = StatisticsUtils.getMonthLabels(trends)
                binding.barChartMonthly.xAxis.valueFormatter = 
                    StatisticsUtils.MonthLabelFormatter(labels)
                
                // 设置柱状图组间距
                val groupSpace = 0.2f
                val barSpace = 0.05f
                val barWidth = 0.35f
                binding.barChartMonthly.barData.barWidth = barWidth
                binding.barChartMonthly.groupBars(0f, groupSpace, barSpace)
                
                binding.barChartMonthly.invalidate()
                binding.barChartMonthly.animateY(1000)
            }
        }

        // 观察状态统计
        viewModel.statusStats.observe(viewLifecycleOwner) { stats ->
            if (stats.isNotEmpty()) {
                val pieData = StatisticsUtils.createStatusPieData(stats)
                binding.doughnutChartStatus.data = pieData
                
                // 设置中心文字
                val total = stats.sumOf { it.count }
                binding.doughnutChartStatus.centerText = "总计\n$total"
                
                binding.doughnutChartStatus.invalidate()
                binding.doughnutChartStatus.animateY(1000)
            }
        }

        // 观察过期率趋势
        viewModel.expiryRateTrends.observe(viewLifecycleOwner) { trends ->
            if (trends.isNotEmpty()) {
                val lineData = StatisticsUtils.createLineData(trends)
                binding.lineChartExpiryRate.data = lineData
                
                // 设置X轴标签
                val labels = trends.map { it.monthLabel }
                binding.lineChartExpiryRate.xAxis.valueFormatter = 
                    StatisticsUtils.MonthLabelFormatter(labels)
                
                binding.lineChartExpiryRate.invalidate()
                binding.lineChartExpiryRate.animateX(1000)
            }
        }

        // 观察加载状态
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // 观察错误信息
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }
    }

    private fun setupRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshStatistics()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        // 每次显示时刷新数据
        viewModel.refreshStatistics()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = StatisticsFragment()
    }
}
