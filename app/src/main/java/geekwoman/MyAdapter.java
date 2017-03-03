package geekwoman;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import jp.co.geekwoman.R;
import twitter4j.QueryResult;
import twitter4j.Status;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context mContext;
    private QueryResult mResult;

    public MyAdapter(Context context, QueryResult result) {
        mContext = context;
        mResult = result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(
                R.layout.list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Status result = mResult.getTweets().get(position);

        holder.textView.setText(result.getText());

        // 非同期処理見づらいと思ってGlide使ったんだけど、、どうかな？
        Glide.with(mContext)
                .load(result.getUser().getOriginalProfileImageURL())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // listenerつけてMainActivityで処理すべきなんだろうけど、、
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("data", result);
                mContext.startActivity(intent);
                // 右から出てくる遷移アニメーションつける？
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResult.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(jp.co.geekwoman.R.id.text_view);
            imageView = (ImageView) itemView.findViewById(jp.co.geekwoman.R.id.image_view);
        }
    }
}
