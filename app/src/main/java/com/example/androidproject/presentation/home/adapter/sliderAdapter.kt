//package com.example.androidproject.presentation.home.adapter
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import com.example.androidproject.R
//import com.example.androidproject.domain.model.SliderObject
//
//class SliderAdapter(
//    private val sliderItems: MutableList<SliderObject>,
//    private val onItemClick: (SliderObject) -> Unit
//) : SliderViewAdapter<SliderAdapter.ViewHolder>() {
//
//    inner class ViewHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
//        private val imageViewBackground: ImageView = itemView.findViewById(R.id.iv_auto_image_slider)
//        private val textViewTitle: TextView = itemView.findViewById(R.id.tv_auto_image_slider)
//        private val textViewSubtitle: TextView = itemView.findViewById(R.id.tv_subtitle)
//
//        fun bindData(sliderItem: SliderObject) {
//            Glide.with(itemView)
//                .load(sliderItem.imageUrl)
//                .fitCenter()
//                .into(imageViewBackground)
//
//            textViewTitle.text = sliderItem.title
//            textViewSubtitle.text = sliderItem.subtitle
//
//            itemView.setOnClickListener { onItemClick(sliderItem) }
//        }
//    }
//
//    fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.image_slider_layout_item, parent, false)
//        return ViewHolder(view)
//    }
//
//    fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        viewHolder.bindData(sliderItems[position])
//    }
//
//    fun getCount(): Int = sliderItems.size
//
//    fun updateItems(newItems: List<SliderObject>) {
//        sliderItems.clear()
//        sliderItems.addAll(newItems)
//        notifyDataSetChanged()
//    }
//}