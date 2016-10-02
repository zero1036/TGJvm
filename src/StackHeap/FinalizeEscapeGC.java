package StackHeap;

/**
 * �˴�����ʾ������ 1����������ڱ�GCʱ�������� 2�������ԾȵĻ���ֻ��һ�Σ���Ϊһ�������finalize()�������ֻ�ᱻϵͳ�Զ�����һ��
 */
public class FinalizeEscapeGC {

	public static FinalizeEscapeGC SAVE_HOOK = null;

	public void isAlive() {
		System.out.println("yes i am still alive");
	}

	public static void main(String[] args) throws Throwable {
		SAVE_HOOK = new FinalizeEscapeGC();

		// �����һ�γɹ������Լ�
		SAVE_HOOK = null;
		System.gc();
		// ��Ϊfinalize�������ȼ��ܵͣ�������ͣ0.5��ȴ���
		Thread.sleep(500);
		if (SAVE_HOOK != null) {
			SAVE_HOOK.isAlive();
		} else {
			System.out.println("no i m dead");
		}

		// ������δ������������ȫ��ͬ����������Ծ�ȴʧ����
		SAVE_HOOK = null;
		System.gc();
		// ��Ϊfinalize�������ȼ��ܵͣ�������ͣ0.5��ȴ���
		Thread.sleep(500);
		if (SAVE_HOOK != null) {
			SAVE_HOOK.isAlive();
		} else {
			System.out.println("no i m dead");
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("finalize method executed");
		FinalizeEscapeGC.SAVE_HOOK = this;
	}
}
