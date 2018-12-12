package wx.query;

/**
 * 查询基础接口
 * @author zhonghui
 *
 */
public interface IStatement {

	public static final int NORMAL = 1;
	public static final int NODE = 2;

	public int getType();

	public String getNodeType();

	public void setNodeType(String nodeType);

	public IStatement getLeft();

	public void setLeft(IStatement left);

	public IStatement getRight();

	public void setRight(IStatement right);

	public IStatement And(IStatement b);

	public IStatement Or(IStatement b);

	public Query asQuery();
}
