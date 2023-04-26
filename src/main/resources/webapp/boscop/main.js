import { basemap } from "./basemap.js";
import { styleFunction } from "./style.js";

const copWfsSource = new ol.source.Vector({
  format: new ol.format.GML32({
    srsName: 'http://www.opengis.net/def/crs/EPSG/0/4326'
  }),
  url: function (extent) {
    return (
      'http://localhost:8080/wfs?SERVICE=WFS&' +
      'VERSION=2.0.2&REQUEST=GetFeature&TYPENAME=boscop:Unit&' +
      'SRSNAME=http://www.opengis.net/def/crs/EPSG/0/4326&' +
      'BBOX=' +
      extent.join(',') +
      ',http://www.opengis.net/def/crs/EPSG/0/4326'
    );
  },
  strategy: ol.loadingstrategy.bbox
});

const copLayer = new ol.layer.Vector({
  source: copWfsSource,
  style: styleFunction
});

const map = new ol.Map({
  target: 'map',
  layers: [
    basemap,
    copLayer
  ],
  view: new ol.View({
    center: [1000000, 6650300],
    zoom: 6
  })
});

const selectSingleClick = new ol.interaction.Select({
  condition: ol.events.condition.pointerMove
});
map.addInteraction(selectSingleClick);

function refreshCopLayer() {
  copLayer.getSource().refresh();
}

let draw;
document.getElementById('insert').addEventListener('click', function () {
  draw = new ol.interaction.Draw({
    source: copWfsSource,
    type: 'Polygon'
  });
  draw.on('drawend', function (e) {
    const formatWFS = new ol.format.WFS({
      version: '2.0.0',
      featureNS: 'urn:ns:de:turnertech:boscop'
    });
    e.feature.setProperties({
      "areaType": typeSelect.value
    });
    e.feature.getGeometry().transform('EPSG:3857', 'EPSG:4326');
    const node = formatWFS.writeTransaction([e.feature], null, null, {
      featureNS: 'urn:ns:de:turnertech:boscop',
      featurePrefix: 'boscop',
      featureType: 'Area',
      srsName: 'http://www.opengis.net/def/crs/EPSG/0/4326',
      version: '2.0.0',
      gmlOptions: {
        featureNS: 'urn:ns:de:turnertech:boscop',
        featureType: 'Area',
        srsName: 'http://www.opengis.net/def/crs/EPSG/0/4326'
      }
    });
    e.feature.getGeometry().transform('EPSG:4326', 'EPSG:3857');
    const xs = new XMLSerializer();
    const payload = xs.serializeToString(node);
    console.log(payload);
    fetch('http://localhost:8080/wfs?SERVICE=WFS&VERSION=2.0.2&REQUEST=Transaction', {
      method: "POST",
      body: payload
    }).then(text => map.removeInteraction(draw));
  });
  map.addInteraction(draw);
});



setInterval(refreshCopLayer, 10000);