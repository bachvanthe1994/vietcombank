package reportConfig;

import java.lang.reflect.Method;
import java.lang.reflect.Constructor;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class RetryListener implements IAnnotationTransformer{
	@SuppressWarnings("rawtypes")
	@Override
public void transform (ITestAnnotation annotation , Class testClass, Constructor testConstructer, Method testMethod)
{
		annotation.setRetryAnalyzer(RetryTestFailed.class);
		}
}
