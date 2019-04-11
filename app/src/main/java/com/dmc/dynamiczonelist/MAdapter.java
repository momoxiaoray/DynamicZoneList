package com.dmc.dynamiczonelist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * .............................................
 * <p>
 * Create by InphaseDai(xiaocheng.ok@qq.com)
 * Date: 2019/4/11 11:11 AM
 * Description: //please input description
 * FIXME
 * ${tags}
 **/
public class MAdapter extends RecyclerView.Adapter<MAdapter.ViewHolder> {


    private List<CommentModel> mCommentModels;
    private Context mContext;
    private OnActionListener mOnActionListener;

    public MAdapter(Context context, List<CommentModel> commentModels) {
        mCommentModels = commentModels;
        mContext = context;
    }


    public void setOnActionListener(OnActionListener listener) {
        mOnActionListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_video_comment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentModel comment = mCommentModels.get(position);
        holder.icon.setOnClickListener(v -> {
            if (mOnActionListener != null)
                mOnActionListener.onUserIconClick(comment.getName());
        });
        holder.name.setText(comment.getName());
        holder.time.setText(comment.getTime());

        holder.commentCount.setOnClickListener(v -> {
            if (mOnActionListener != null)
                mOnActionListener.onComment(comment);
        });
        holder.zanCount.setOnClickListener(v -> {
            if (mOnActionListener != null) {
                mOnActionListener.onHit(comment);
            }
        });
        String content = comment.getContent();
        if (content.length() > 100) {
            SpannableStringBuilder contentSb = new SpannableStringBuilder();
            contentSb.append(content.substring(0, 30)).append("...");
            String firstStr = "全文";
            SpannableStringBuilder firstSsb = new SpannableStringBuilder(firstStr);
            firstSsb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {
                    if (mOnActionListener != null)
                        mOnActionListener.onDetailClick(comment);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                    ds.setUnderlineText(false);
                }
            }, 0, firstStr.length(), 0);
            contentSb.append(firstSsb);
            holder.content.setText(contentSb);
        } else {
            holder.content.setText(content);
        }

        holder.view.setOnClickListener(v -> {
            if (mOnActionListener != null)
                mOnActionListener.onDetailClick(comment);
        });

        holder.view.setOnLongClickListener(v -> {
            if (mOnActionListener != null)
                mOnActionListener.onLongClick(comment);
            return true;
        });

        refreshItemView(holder, comment);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            //局部刷新，
            Object object = payloads.get(0);
            if (object instanceof CommentModel) {
                refreshItemView(holder, mCommentModels.get(position));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mCommentModels == null ? 0 : mCommentModels.size();
    }


    private void refreshItemView(ViewHolder holder, CommentModel comment) {
        //得到drawable对象，即所要插入的图片
        Drawable d;
        if (comment.isHit()) {
            d = mContext.getResources().getDrawable(R.mipmap.icon_video_zan_ed);
        } else {
            d = mContext.getResources().getDrawable(R.mipmap.icon_video_zan);
        }
        holder.zanCount.setCompoundDrawablesWithIntrinsicBounds(d,
                null, null, null);
        holder.zanCount.setCompoundDrawablePadding(mContext.getResources().getDimensionPixelOffset(R.dimen.dp_4));
        holder.zanCount.setText(String.valueOf(comment.getHitCount()));
        //回复
        holder.commentCount.setText(String.valueOf(comment.getCommentCount()));
        //第一级的时候会有评论的回复内容，需展示
        List<CommentModel.Reply> replays = comment.getReplys();
        if (replays != null && replays.size() > 0) {
            holder.contentReply.setVisibility(View.VISIBLE);
            SpannableStringBuilder builder = new SpannableStringBuilder();
            for (int i = 0; i < replays.size(); i++) {
                CommentModel.Reply replay = replays.get(i);
                SpannableStringBuilder ssb = new SpannableStringBuilder();
                String posterName = replay.getName();
                // 发表评论的人名长度
                int posterNameLength = posterName.length();
                //把评论发送者名字添加进去
                ssb.append(posterName);
                ssb.setSpan(new ClickableSpan() {

                    @Override
                    public void onClick(View widget) {
                        if (mOnActionListener != null)
                            mOnActionListener.onUserIconClick(posterName);
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        // TODO Auto-generated method stub
                        super.updateDrawState(ds);
                        ds.setColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                        ds.setUnderlineText(false);
                    }
                }, 0, posterNameLength, 0);
                if (replay.getType() == 1) {
                    String reply = " " + mContext.getString(R.string.recover) + " ";
                    //添加“回复”二字
                    ssb.append(reply);
                    ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.grey)),
                            posterNameLength, reply.length() + posterNameLength, 0);
                    //把被回复的名字添加进去
                    String replyName = replay.getToName();
                    ssb.append(replyName);
                    ssb.setSpan(new ClickableSpan() {

                        @Override
                        public void onClick(View widget) {
                            if (mOnActionListener != null)
                                mOnActionListener.onUserIconClick(replyName);
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                            ds.setUnderlineText(false);
                        }
                    }, reply.length() + posterNameLength, reply.length() + posterNameLength + replyName.length(), 0);
                }
                //内容
                ssb.append(": ");
                int wordsLength = replay.getContent().length();
                SpannableString sbContent = new SpannableString(replay.getContent());
                ssb.append(sbContent);
                builder.append(ssb);
                if (i != replays.size() - 1)
                    builder.append("\n");
            }
            //如果回复数量大于返回显示的数量，则显示"展开**条回复"
            if (comment.getCommentCount() > replays.size()) {
                builder.append("\n");
                SpannableStringBuilder ssb = new SpannableStringBuilder();
                String more = "展开" + comment.getCommentCount() + "条回复";
                ssb.append(more);
                ssb.setSpan(new ClickableSpan() {

                    @Override
                    public void onClick(View widget) {
                        if (mOnActionListener != null)
                            mOnActionListener.onDetailClick(comment);
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        // TODO Auto-generated method stub
                        super.updateDrawState(ds);
                        ds.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                        ds.setUnderlineText(false);
                    }
                }, 0, more.length(), 0);
                builder.append(ssb);
            }
            holder.contentReply.setText(builder);
        } else {
            holder.contentReply.setVisibility(View.GONE);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        TextView time;
        TextView commentCount, zanCount;
        TextView content;
        TextView contentReply;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            icon = itemView.findViewById(R.id.item_icon);
            name = itemView.findViewById(R.id.item_name);
            time = itemView.findViewById(R.id.item_time);
            commentCount = itemView.findViewById(R.id.item_comment);
            zanCount = itemView.findViewById(R.id.item_zan);
            content = itemView.findViewById(R.id.item_content);
            contentReply = itemView.findViewById(R.id.item_content_reply);
        }
    }


    public interface OnActionListener {
        void onComment(CommentModel item);

        void onHit(CommentModel item);

        void onDetailClick(CommentModel item);

        void onAllReplyOpen(CommentModel item);

        void onUserIconClick(String userName);

        void onLongClick(CommentModel item);
    }
}
