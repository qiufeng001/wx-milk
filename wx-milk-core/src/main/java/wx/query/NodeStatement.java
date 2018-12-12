package wx.query;

import java.io.Serializable;

public class NodeStatement implements IStatement, Serializable {

	private static final long serialVersionUID = -7324488914367578649L;

	String nodeType;

	protected NodeStatement() {

	}

	public NodeStatement(IStatement a) {
		this.left = a;
	}

	public NodeStatement(IStatement a, String nodeType, IStatement b) {
		this.left = a;
		this.right = b;
		this.nodeType = nodeType;
	}

	@Override
	public int getType() {
		return IStatement.NODE;
	}

	@Override
	public String getNodeType() {
		return nodeType;
	}

	@Override
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	private IStatement left;
	private IStatement right;
	@Override
	public IStatement getLeft() {
		return left;
	}
	@Override
	public void setLeft(IStatement left) {
		this.left = left;
	}
	@Override
	public IStatement getRight() {
		return right;
	}
	@Override
	public void setRight(IStatement right) {
		this.right = right;
	}
	@Override
	public IStatement And(IStatement b) {
		NodeStatement st = new NodeStatement(this, "AND", b);
		return st;
	}
	@Override
	public IStatement Or(IStatement b) {
		NodeStatement st = new NodeStatement(this, "OR", b);
		return st;
	}

	@Override
	public String toString() {
		if (this.right == null) {
			return String.format("(%s)", this.left.toString());
		}
		return String.format("(%s %s %s)", this.left.toString(), this.nodeType, this.right.toString());
	}

	@Override
	public Query asQuery() {
		return Q.where(this);
	}

}
