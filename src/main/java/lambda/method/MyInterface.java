package lambda.method;

public interface MyInterface<T, R>
{
	R test(T t);
}


interface MyInterface1<T>
{
	T get();
}