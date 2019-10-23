package com.ysxsoft.grainandoil.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.jessyan.autosize.utils.LogUtils;

/**
 * create by Sincerly on 2016/12/29 0034
 **/
public abstract class RBaseAdapter<T> extends RecyclerView.Adapter<RViewHolder> {
	private List<T> data;
	private Context context;
	private int layoutId;
	private LayoutInflater inflater;
	private OnItemClickListener onItemClickListener;
	private onItemLongClickListener onItemLongClickListener;
	public static final int NORMAL = 0;
	public static final int AD = 1;

	public RBaseAdapter(Context c, int layoutId, List<T> data) {
		this.context = c;
		this.layoutId = layoutId;
		this.data = data;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RViewHolder holder = null;
		switch (viewType) {
			case NORMAL:
				//正常情况下
				holder = new RViewHolder(inflater.inflate(layoutId, parent, false));
				break;
			case AD:
				//广告
//				holder = new RViewHolder(inflater.inflate(R.layout.item_tab1_system_message, parent, false));
				break;
			default:
				break;
		}
		if (holder != null) {
			holder.setType(viewType);
		}
		//瀑布流更改image
		holder = changeImageView(holder);
		return holder;
	}

	protected RViewHolder changeImageView(RViewHolder holder) {
		return holder;
	}

	@Override
	public void onBindViewHolder(RViewHolder holder, int position) {
		T item = getItem(position);
		if (item == null) {
			return;
		}
		fillItem(holder, item, position);
		if (onItemClickListener != null) {
			setOnItemClick(holder, holder.getConvertView(), position);
		}
		if (onItemLongClickListener != null) {
			setOnItemLongClick(holder, holder.getConvertView(), position);
		}
	}

	@Override
	public int getItemViewType(int position) {
		T d = data.get(position);
		return getViewType(d, position);
	}

	public void setOnItemClick(final RViewHolder holder, final View view, final int position) {
		holder.getConvertView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onItemClickListener.onItemClick(holder, v, position);
			}
		});
	}

	public void setOnItemLongClick(final RViewHolder holder, final View view, final int position) {
		holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return onItemLongClickListener.onItemLongClick(holder, view, position);
			}
		});
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	private T getItem(int position) {
		if (data != null && data.size() > 0) {
			return data.get(position);
		}
		return null;
	}

	public interface OnItemClickListener {
		void onItemClick(RViewHolder holder, View view, int position);
	}

	public interface onItemLongClickListener {
		boolean onItemLongClick(RViewHolder holder, View view, int position);
	}

	public void setOnItemLongClickListener(RBaseAdapter.onItemLongClickListener onItemLongClickListener) {
		this.onItemLongClickListener = onItemLongClickListener;
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.onItemClickListener = listener;
	}

	public void attach(RecyclerView recyclerView) {
		recyclerView.getAdapter().registerAdapterDataObserver(adapterDataObserver);
	}

	public void dettach(RecyclerView recyclerView) {
		recyclerView.getAdapter().unregisterAdapterDataObserver(adapterDataObserver);
	}

	/**
	 * 数据监听器
	 */
	private RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() {
		@Override
		public void onChanged() {
			super.onChanged();
			LogUtils.e("onChanged");
		}

		@Override
		public void onItemRangeChanged(int positionStart, int itemCount) {
			super.onItemRangeChanged(positionStart, itemCount);
			LogUtils.e("onItemRangeChanged");
		}

		@Override
		public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
			super.onItemRangeChanged(positionStart, itemCount, payload);
			LogUtils.e("onItemRangeChanged 3params");
		}

		@Override
		public void onItemRangeInserted(int positionStart, int itemCount) {
			super.onItemRangeInserted(positionStart, itemCount);
			LogUtils.e("onItemRangeInserted");
		}

		@Override
		public void onItemRangeRemoved(int positionStart, int itemCount) {
			super.onItemRangeRemoved(positionStart, itemCount);
			LogUtils.e("onItemRangeRemoved");
		}

		@Override
		public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
			super.onItemRangeMoved(fromPosition, toPosition, itemCount);
			LogUtils.e("onItemRangeMoved");
		}
	};

	protected abstract void fillItem(RViewHolder holder, T item, int position);
	protected abstract int getViewType(T item, int position);
}