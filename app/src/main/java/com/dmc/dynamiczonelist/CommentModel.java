package com.dmc.dynamiczonelist;

import java.util.List;

/**
 * .............................................
 * <p>
 * Create by InphaseDai(xiaocheng.ok@qq.com)
 * Date: 2019/4/11 11:20 AM
 * Description: //please input description
 * FIXME
 * ${tags}
 **/
public class CommentModel {


    private String name;
    private int commentCount;
    private int hitCount;
    private String time;
    private boolean isHit = false;
    private List<Reply> comments;
    private String content;

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public List<Reply> getReplys() {
        return comments;
    }

    public void setComments(List<Reply> comments) {
        this.comments = comments;
    }

    public static class Reply {
        /**
         * name : Google
         * type : 1
         * toName : Baidu
         * content : lalalallaskdjaklsjdlkasjdlkasdjkalsjd
         */

        private String name;
        //0代表是评论，1代表是回复
        private int type;
        private String toName;
        private String content;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getToName() {
            return toName;
        }

        public void setToName(String toName) {
            this.toName = toName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
