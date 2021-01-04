package com.example.caipu.info;

import java.util.List;

public class ShowCookersInfo {
    private String resultcode;
    private String mes;
    private Result result;
    private int error_code;
    public static class Result {
        private List<Data> list;
        private String totalNum;
        private String pn;
        private String num;

        public static class Data {
            private String id;
            private String classid;
            private String name;
            private String tag;
            private String content;
            private String preparetime;
            private String cookingtime;
            private String pic;
            private List<Steps> process;


            public static class Steps {
                private String pic;
                private String pcontent;

                public String getImg() {
                    return pic;
                }

                public void setImg(String img) {
                    this.pic = img;
                }

                public String getStep() {
                    return pcontent;
                }

                public void setStep(String step) {
                    this.pcontent = step;
                }
            }


            public void setclassId(String classid){this.classid = classid;}
            public String getClassId(){return classid;}
            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return name;
            }

            public void setTitle(String title) {
                this.name = title;
            }

            public String getTags() {
                return tag;
            }

            public void setTags(String tags) {
                this.tag = tags;
            }

            public String getImtro() {
                return content;
            }

            public void setImtro(String imtro) {
                this.content = imtro;
            }

            public String getIngredients() {
                return preparetime;
            }

            public void setIngredients(String ingredients) {
                this.preparetime = ingredients;
            }

            public String getBurden() {
                return cookingtime;
            }

            public void setBurden(String burden) {
                this.cookingtime = burden;
            }

            public String getAlbums() {
                return pic;
            }

            public void setAlbums(String albums) {
                this.pic = albums;
            }

            public List<Steps> getSteps() {
                return process;
            }

            public void setSteps(List<Steps> steps) {
                this.process = steps;
            }
        }

        public List<Data> getData() {
            return list;
        }

        public void setData(List<Data> data) {
            this.list = data;
        }

        public String getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(String totalNum) {
            this.totalNum = totalNum;
        }

        public String getPn() {
            return pn;
        }

        public void setPn(String pn) {
            this.pn = pn;
        }

        public String getRn() {
            return num;
        }

        public void setRn(String rn) {
            this.num = rn;
        }
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return mes;
    }

    public void setReason(String reason) {
        this.mes = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
