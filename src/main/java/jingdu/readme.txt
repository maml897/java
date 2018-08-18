1.BigDecimal(String val)	参数可以是double，或者String，但是double会有问题
2.DecimalFormat.format		格式化方法 ，返回字符串，参数可以是double，或者Object，但是double会有问题，参见DoubleError.java；最好是上面1对象


最终要用这个方法 DecimalFormat.format(new BigDecimal(String val)) // 转成字符串