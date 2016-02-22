function main(properties) {
	var pdf = Packages.at.pkgs.pdf;
	var factory;
	var document;

	factory = new pdf.template.FieldProviderFactory();
	factory.loadTextStyleProvider(properties, 'style');
	document = new pdf.builder.DocumentModel();
	document.add(
			new pdf.builder.DocumentModel.Font(
					'monospaced.regular',
					'/at/pkgs/pdf/builder/font/migmix-1m-regular.ttf',
					'Identity-H',
					true));
	(function() {
		var fields;
		var data;
		var values;

		fields = factory.createFieldProvider(properties, 'page');
		data = {
				date: new java.util.Date(),
		};
		values = new (Java.extend(pdf.template.NamedValueProvider, {
			get: function(name) {
				return data[name];
			},
		}));
		fields.merge(document, values);
	})();
	(function() {
		var fields;
		var height;
		var data;
		var values;
		var index;

		fields = factory.createFieldProvider(properties, 'table', 'height');
		height = parseFloat(properties.getProperty('table.height'));
		data = {
				no: null,
		};
		values = new (Java.extend(pdf.template.NamedValueProvider, {
			get: function(name) {
				return data[name];
			},
		}));
		for (index = 0; index < 20; index ++) {
			data.no = index + 1;
			fields.merge(0, height * index, document, values);
		}
	})();
	(function() {
		pdf.template.BarcodeStamper
				.parse(
						new Packages.at.pkgs.barcode.Code39().checkDigit(true),
						properties.getProperty('barcode'))
				.merge(document, '0123456789');
	})();
	return document;
}
