1.BigDecimal(String val)	参数可以是double，或者String，但是double会有问题,有什么问题目前不清楚，没有实例，但是 DecimalFormat.format(new BigDecimal(double val)) 会有问题，参见DoubleError.java
2.DecimalFormat.format		格式化方法 ，返回字符串，参数可以是double，或者Object，但是double会有问题，参见DoubleError.java；最好是上面1对象


所以最终要用这个方法 DecimalFormat.format(new BigDecimal(String val)) // 转成字符串，如果再想转回取，则使用 BigDecimal(String val).floatValue()