// js方法：序列化表单
function serializeForm(form) {
	var obj = {};
	$.each(form.serializeArray(), function(index) {
				if (obj[this['name']]) {
					obj[this['name']] = obj[this['name']] + ',' + this['value'];
				} else {
					obj[this['name']] = this['value'];
				}
			});
	return obj;
}