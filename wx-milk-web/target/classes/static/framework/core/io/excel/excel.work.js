var excelWork = {};

var encodings = {
    '&quot;': '"',
    '&apos;': "'",
    '&gt;': '>',
    '&lt;': '<',
    '&amp;': '&'
};
var rencoding = evert(encodings);
//var rencstr = "&<>'\"".split("");

function keys(o) {
    return Object.keys(o);
}

//function evert_key(obj, key) {
//    var o = [], K = keys(obj);
//    for (var i = 0; i !== K.length; ++i) o[obj[K[i]][key]] = K[i];
//    return o;
//}

function evert(obj) {
    var o = [], K = keys(obj);
    for (var i = 0; i !== K.length; ++i) o[obj[K[i]]] = K[i];
    return o;
}

//function evert_num(obj) {
//    var o = [], K = keys(obj);
//    for (var i = 0; i !== K.length; ++i) o[obj[K[i]]] = parseInt(K[i], 10);
//    return o;
//}
//
//function evert_arr(obj) {
//    var o = [], K = keys(obj);
//    for (var i = 0; i !== K.length; ++i) {
//        if (o[obj[K[i]]] == null) o[obj[K[i]]] = [];
//        o[obj[K[i]]].push(K[i]);
//    }
//    return o;
//}

function isval(x) {
    return x !== undefined && x !== null;
}

function writetag(f, g) {
    return '<' + f + (g.match(wtregex) ? ' xml:space="preserve"' : "") + '>' + g + '</' + f + '>';
}
//function get_cell_style(styles, cell, opts) {
//    var z = opts.revssf[cell.z != null ? cell.z : "General"];
//    // for (var i = 0, len = styles.length; i != len; ++i) if (styles[i].numFmtId === z) return i;
//    var len = styles.length;
//    var key = "_styles"
//    if( styles.hasOwnProperty(key) && styles[styles][z]){
//        return styles[key][z].i;
//    }
//    if(!styles.hasOwnProperty(key)){
//        styles[key] = {};
//    }
//
//    styles[len] = {
//        numFmtId: z,
//        fontId: 0,
//        fillId: 0,
//        borderId: 0,
//        xfId: 0,
//        applyNumberFormat: 1,
//        i:len
//    };
//    return len;
//}
//function get_sst_id(sst, str) {
//    var len = sst.length;
//    if( sst.hasOwnProperty("_keys") && sst._keys[str]){
//        sst.Count ++;
//        return sst._keys[str].i;
//    }
//    if(!sst.hasOwnProperty("_keys")){
//        sst._keys = {};
//    }
//    sst[len] = {t: str,i:len};
//    sst.Count++;
//    sst.Unique++;
//    sst._keys[str] = sst[len];
//    return len;
//}

var decregex = /[&<>'"]/g, charegex = /[\u0000-\u0008\u000b-\u001f]/g;
function escapexml(text) {
    var s = text + '';
    return s.replace(decregex, function (y) {
        return rencoding[y];
    }).replace(charegex, function (s) {
        return "_x" + ("000" + s.charCodeAt(0).toString(16)).substr(-4) + "_";
    });
}


/* TODO: date1904 logic */
function datenum(v, date1904) {
    if (date1904) v += 1462;
    var epoch = Date.parse(v);
    return (epoch + 2209161600000) / (24 * 60 * 60 * 1000);
}

function write_ws_xml_cell(cell, ref, ws, opts, idx, wb) {
    if (cell.v === undefined) return "";
    var vv = "";
    var oldt = cell.t, oldv = cell.v;
    switch (cell.t) {
        case 'b':
            vv = cell.v ? "1" : "0";
            break;
        case 'n':
            vv = '' + cell.v;
            break;
        case 'e':
            vv = BErr[cell.v];
            break;
        case 'd':
            if (opts.cellDates) vv = new Date(cell.v).toISOString();
            else {
                cell.t = 'n';
                vv = '' + (cell.v = datenum(cell.v));
                if (typeof cell.z === 'undefined') cell.z = SSF._table[14];
            }
            break;
        default:
            vv = cell.v;
            break;
    }
    var v = writetag('v', escapexml(vv)), o = {r: ref};
    /* TODO: cell style */
    var os = cell.ss; //get_cell_style(opts.cellXfs, cell, opts);
    if (os !== 0) o.s = os;
    switch (cell.t) {
        case 'n':
            break;
        case 'd':
            o.t = "d";
            break;
        case 'b':
            o.t = "b";
            break;
        case 'e':
            o.t = "e";
            break;
        default:
            if (opts.bookSST) {
                //v = writetag('v', '' + get_sst_id(opts.Strings, cell.v));
                v = writetag('v',"" + cell.sv);
                o.t = "s";
                break;
            }
            o.t = "str";
            break;
    }
    if (cell.t != oldt) {
        cell.t = oldt;
        cell.v = oldv;
    }
    return writextag('c', v, o);
}

function wxt_helper(h) {
    return keys(h).map(function (k) {
        return " " + k + '="' + h[k] + '"';
    }).join("");
}

function encode_row(row) {
    return "" + (row + 1);
}

var wtregex = /(^\s|\s$|\n)/;

function writextag(f, g, h) {
    return '<' + f + (isval(h) ? wxt_helper(h) : "") + (isval(g) ? (g.match(wtregex) ? ' xml:space="preserve"' : "") + '>' + g + '</' + f : "/") + '>';
}


self.addEventListener('message', function (e) {
    if(e.data == null ){
        self.postMessage(null);
        return;
    }
    var ws = e.data.ws;
    var opts = e.data.opts;
    var idx = e.data.idx;
    var start = e.data.pageStart;
    var end = e.data.pageEnd;

    var o = [], r = [], range = e.data.range, cell, ref, rr = "", cols = e.data.cols, R, C;

    for (R = start; R <  end; ++R) {
        r = [];
        rr = encode_row(R);
        for (C = range.s.c; C <= range.e.c; ++C) {
            ref = cols[C] + rr;
            if (ws[ref] === undefined) continue;
            if ((cell = write_ws_xml_cell(ws[ref], ref, ws, opts, idx)) != null) r.push(cell);
        }
        if (r.length > 0) o[o.length] = (writextag('row', r.join(""), {r: rr}));
    }
    var val = o.join("");
    self.postMessage(val);
});



