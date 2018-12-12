package wx.base.excel;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonSerializer;

@SuppressWarnings("rawtypes")
public class JsonDataFormater implements IDataFormater {

	JsonSerializer js;

	public JsonDataFormater(JsonSerializer js) {
		this.js = js;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object format(Object val) {
		if (val == null)
			return null;
		ExcelJsonGenerator gen = new ExcelJsonGenerator();
		try {
			js.serialize(val, gen, null);
		} catch (IOException e) {
			return null;
		}
		return gen.getValue();
	}

	public class ExcelJsonGenerator extends JsonGenerator {

		@Override
		public JsonGenerator setCodec(ObjectCodec oc) {
			return null;
		}

		@Override
		public ObjectCodec getCodec() {
			return null;
		}

		@Override
		public Version version() {
			return null;
		}

		@Override
		public JsonGenerator enable(Feature f) {
			return null;
		}

		@Override
		public JsonGenerator disable(Feature f) {
			return null;
		}

		@Override
		public boolean isEnabled(Feature f) {
			return false;
		}

		@Override
		public int getFeatureMask() {
			return 0;
		}

		@Override
		public JsonGenerator setFeatureMask(int values) {
			return null;
		}

		@Override
		public JsonGenerator useDefaultPrettyPrinter() {
			return null;
		}

		@Override
		public void writeStartArray() throws IOException {

		}

		@Override
		public void writeEndArray() throws IOException {

		}

		@Override
		public void writeStartObject() throws IOException {
		}

		@Override
		public void writeEndObject() throws IOException {
		}

		@Override
		public void writeFieldName(String name) throws IOException {
		}

		@Override
		public void writeFieldName(SerializableString name) throws IOException {
		}

		private Object val;

		@Override
		public void writeString(String text) throws IOException {
			this.val = text;
		}

		public Object getValue() {
			return this.val;
		}

		@Override
		public void writeString(char[] text, int offset, int len) throws IOException {

		}

		@Override
		public void writeString(SerializableString text) throws IOException {

		}

		@Override
		public void writeRawUTF8String(byte[] text, int offset, int length) throws IOException {

		}

		@Override
		public void writeUTF8String(byte[] text, int offset, int length) throws IOException {

		}

		@Override
		public void writeRaw(String text) throws IOException {
			this.val = text;
		}

		@Override
		public void writeRaw(String text, int offset, int len) throws IOException {

		}

		@Override
		public void writeRaw(char[] text, int offset, int len) throws IOException {

		}

		@Override
		public void writeRaw(char c) throws IOException {

		}

		@Override
		public void writeRawValue(String text) throws IOException {

		}

		@Override
		public void writeRawValue(String text, int offset, int len) throws IOException {

		}

		@Override
		public void writeRawValue(char[] text, int offset, int len) throws IOException {

		}

		@Override
		public void writeBinary(Base64Variant bv, byte[] data, int offset, int len) throws IOException {

		}

		@Override
		public int writeBinary(Base64Variant bv, InputStream data, int dataLength) throws IOException {
			return 0;
		}

		@Override
		public void writeNumber(int v) throws IOException {
			this.val = v;
		}

		@Override
		public void writeNumber(long v) throws IOException {
			this.val = v;

		}

		@Override
		public void writeNumber(BigInteger v) throws IOException {
			this.val = v;
		}

		@Override
		public void writeNumber(double v) throws IOException {
			this.val = v;

		}

		@Override
		public void writeNumber(float v) throws IOException {
			this.val = v;

		}

		@Override
		public void writeNumber(BigDecimal v) throws IOException {
			this.val = v;

		}

		@Override
		public void writeNumber(String encodedValue) throws IOException {
			this.val = encodedValue;

		}

		@Override
		public void writeBoolean(boolean state) throws IOException {
			this.val = state;

		}

		@Override
		public void writeNull() throws IOException {

		}

		@Override
		public void writeObject(Object pojo) throws IOException {
			this.val = pojo;

		}

		@Override
		public void writeTree(TreeNode rootNode) throws IOException {
			this.val = rootNode;

		}

		@Override
		public JsonStreamContext getOutputContext() {
			return null;
		}

		@Override
		public void flush() throws IOException {

		}

		@Override
		public boolean isClosed() {
			return false;
		}

		@Override
		public void close() throws IOException {

		}
	}
}
