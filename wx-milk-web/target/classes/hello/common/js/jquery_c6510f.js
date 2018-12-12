!function (e, t) {
    "object" == typeof module && "object" == typeof module.exports ? module.exports = e.document ? t(e, !0) : function (e) {
        if (!e.document) throw new Error("jQuery requires a window with a document");
        return t(e)
    } : t(e)
}("undefined" != typeof window ? window : this, function (e, t) {
    function n(e) {
        var t = "length" in e && e.length, n = se.type(e);
        return "function" === n || se.isWindow(e) ? !1 : "array" === n || 0 === t || "number" == typeof t && t > 0 && t - 1 in e
    }

    function r(e, t, n) {
        if (se.isFunction(t)) return se.grep(e, function (e, r) {
            return !!t.call(e, r, e) !== n
        });
        if (t.nodeType) return se.grep(e, function (e) {
            return e === t !== n
        });
        if ("string" == typeof t) {
            if (he.test(t)) return se.filter(t, e, n);
            t = se.filter(t, e)
        }
        return se.grep(e, function (e) {
            return ee.call(t, e) > -1 !== n
        })
    }

    function i(e, t) {
        for (; (e = e[t]) && 1 !== e.nodeType;) ;
        return e
    }

    function o(e) {
        var t = {};
        return se.each(e.match(be) || [], function (e, n) {
            t[n] = !0
        }), t
    }

    function s(e) {
        return e
    }

    function a(e) {
        throw e
    }

    function u() {
        Q.removeEventListener("DOMContentLoaded", u), e.removeEventListener("load", u), se.ready()
    }

    function l() {
        this.expando = se.expando + l.uid++
    }

    function c(e, t, n) {
        var r;
        if (void 0 === n && 1 === e.nodeType) if (r = "data-" + t.replace(De, "-$&").toLowerCase(), n = e.getAttribute(r), "string" == typeof n) {
            try {
                n = "true" === n ? !0 : "false" === n ? !1 : "null" === n ? null : +n + "" === n ? +n : Ee.test(n) ? se.parseJSON(n) : n
            } catch (i) {
            }
            ke.set(e, t, n)
        } else n = void 0;
        return n
    }

    function f(e, t, n, r) {
        var i, o = 1, s = 20, a = r ? function () {
                return r.cur()
            } : function () {
                return se.css(e, t, "")
            }, u = a(), l = n && n[3] || (se.cssNumber[t] ? "" : "px"),
            c = (se.cssNumber[t] || "px" !== l && +u) && Se.exec(se.css(e, t));
        if (c && c[3] !== l) {
            l = l || c[3], n = n || [], c = +u || 1;
            do o = o || ".5", c /= o, se.style(e, t, c + l); while (o !== (o = a() / u) && 1 !== o && --s)
        }
        return n && (c = +c || +u || 0, i = n[1] ? c + (n[1] + 1) * n[2] : +n[2], r && (r.unit = l, r.start = c, r.end = i)), i
    }

    function p(e, t) {
        for (var n, r, i = [], o = 0, s = e.length; s > o; o++) r = e[o], r.style && (n = r.style.display, t ? "none" === n && (i[o] = Ce.get(r, "display") || "") : "none" !== n && (i[o] = "none", Ce.set(r, "display", n)));
        for (o = 0; s > o; o++) null != i[o] && (e[o].style.display = i[o]);
        return e
    }

    function d(e, t) {
        var n = "undefined" != typeof e.getElementsByTagName ? e.getElementsByTagName(t || "*") : "undefined" != typeof e.querySelectorAll ? e.querySelectorAll(t || "*") : [];
        return void 0 === t || t && se.nodeName(e, t) ? se.merge([e], n) : n
    }

    function h(e, t) {
        for (var n = 0, r = e.length; r > n; n++) Ce.set(e[n], "globalEval", !t || Ce.get(t[n], "globalEval"))
    }

    function g(e, t, n, r, i) {
        for (var o, s, a, u, l, c, f = t.createDocumentFragment(), p = [], g = 0, m = e.length; m > g; g++) if (o = e[g], o || 0 === o) if ("object" === se.type(o)) se.merge(p, o.nodeType ? [o] : o); else if (Re.test(o)) {
            for (s = s || f.appendChild(t.createElement("div")), a = (He.exec(o) || ["", ""])[1].toLowerCase(), u = Oe[a] || Oe._default, s.innerHTML = u[1] + se.htmlPrefilter(o) + u[2], c = u[0]; c--;) s = s.lastChild;
            se.merge(p, s.childNodes), s = f.firstChild, s.textContent = ""
        } else p.push(t.createTextNode(o));
        for (f.textContent = "", g = 0; o = p[g++];) if (r && se.inArray(o, r) > -1) i && i.push(o); else if (l = se.contains(o.ownerDocument, o), s = d(f.appendChild(o), "script"), l && h(s), n) for (c = 0; o = s[c++];) Fe.test(o.type || "") && n.push(o);
        return f
    }

    function m() {
        return !0
    }

    function v() {
        return !1
    }

    function y() {
        try {
            return Q.activeElement
        } catch (e) {
        }
    }

    function x(e, t, n, r, i, o) {
        var s, a;
        if ("object" == typeof t) {
            "string" != typeof n && (r = r || n, n = void 0);
            for (a in t) x(e, a, n, r, t[a], o);
            return e
        }
        return null == r && null == i ? (i = n, r = n = void 0) : null == i && ("string" == typeof n ? (i = r, r = void 0) : (i = r, r = n, n = void 0)), i === !1 && (i = v), 1 === o && (s = i, i = function (e) {
            return se().off(e), s.apply(this, arguments)
        }, i.guid = s.guid || (s.guid = se.guid++)), e.each(function () {
            se.event.add(this, t, i, r, n)
        })
    }

    function b(e, t) {
        return se.nodeName(e, "table") && se.nodeName(11 !== t.nodeType ? t : t.firstChild, "tr") ? e.getElementsByTagName("tbody")[0] || e : e
    }

    function w(e) {
        return e.type = (null !== e.getAttribute("type")) + "/" + e.type, e
    }

    function T(e) {
        var t = Xe.exec(e.type);
        return t ? e.type = t[1] : e.removeAttribute("type"), e
    }

    function C(e, t) {
        var n, r, i, o, s, a, u, l;
        if (1 === t.nodeType) {
            if (Ce.hasData(e) && (o = Ce.access(e), s = Ce.set(t, o), l = o.events)) {
                delete s.handle, s.events = {};
                for (i in l) for (n = 0, r = l[i].length; r > n; n++) se.event.add(t, i, l[i][n])
            }
            ke.hasData(e) && (a = ke.access(e), u = se.extend({}, a), ke.set(t, u))
        }
    }

    function k(e, t) {
        var n = t.nodeName.toLowerCase();
        "input" === n && Le.test(e.type) ? t.checked = e.checked : "input" !== n && "textarea" !== n || (t.defaultValue = e.defaultValue)
    }

    function E(e, t, n, r) {
        t = K.apply([], t);
        var i, o, s, a, u, l, c = 0, f = e.length, p = f - 1, h = t[0], m = se.isFunction(h);
        if (m || f > 1 && "string" == typeof h && !ie.checkClone && _e.test(h)) return e.each(function (i) {
            var o = e.eq(i);
            m && (t[0] = h.call(this, i, o.html())), E(o, t, n, r)
        });
        if (f && (i = g(t, e[0].ownerDocument, !1, e, r), o = i.firstChild, 1 === i.childNodes.length && (i = o), o || r)) {
            for (s = se.map(d(i, "script"), w), a = s.length; f > c; c++) u = i, c !== p && (u = se.clone(u, !0, !0), a && se.merge(s, d(u, "script"))), n.call(e[c], u, c);
            if (a) for (l = s[s.length - 1].ownerDocument, se.map(s, T), c = 0; a > c; c++) u = s[c], Fe.test(u.type || "") && !Ce.access(u, "globalEval") && se.contains(l, u) && (u.src ? se._evalUrl && se._evalUrl(u.src) : se.globalEval(u.textContent.replace(ze, "")))
        }
        return e
    }

    function D(e, t, n) {
        for (var r, i = t ? se.filter(t, e) : e, o = 0; null != (r = i[o]); o++) n || 1 !== r.nodeType || se.cleanData(d(r)), r.parentNode && (n && se.contains(r.ownerDocument, r) && h(d(r, "script")), r.parentNode.removeChild(r));
        return e
    }

    function N(e, t, n) {
        var r, i, o, s, a = e.style;
        return n = n || Ye(e), n && (s = n.getPropertyValue(t) || n[t], "" !== s || se.contains(e.ownerDocument, e) || (s = se.style(e, t)), !ie.pixelMarginRight() && Ve.test(s) && Ue.test(t) && (r = a.width, i = a.minWidth, o = a.maxWidth, a.minWidth = a.maxWidth = a.width = s, s = n.width, a.width = r, a.minWidth = i, a.maxWidth = o)), void 0 !== s ? s + "" : s
    }

    function S(e, t) {
        return {
            get: function () {
                return e() ? void delete this.get : (this.get = t).apply(this, arguments)
            }
        }
    }

    function j(e) {
        if (e in tt) return e;
        for (var t = e[0].toUpperCase() + e.slice(1), n = et.length; n--;) if (e = et[n] + t, e in tt) return e
    }

    function A(e, t, n) {
        var r = Je.exec(t);
        return r ? Math.max(0, r[1] - (n || 0)) + (r[2] || "px") : t
    }

    function q(e, t, n, r, i) {
        for (var o = n === (r ? "border" : "content") ? 4 : "width" === t ? 1 : 0, s = 0; 4 > o; o += 2) "margin" === n && (s += se.css(e, n + je[o], !0, i)), r ? ("content" === n && (s -= se.css(e, "padding" + je[o], !0, i)), "margin" !== n && (s -= se.css(e, "border" + je[o] + "Width", !0, i))) : (s += se.css(e, "padding" + je[o], !0, i), "padding" !== n && (s += se.css(e, "border" + je[o] + "Width", !0, i)));
        return s
    }

    function L(t, n, r) {
        var i, o = !0, s = Ye(t), a = "border-box" === se.css(t, "boxSizing", !1, s);
        if (t.getClientRects().length && (i = t.getBoundingClientRect()[n]), Q.msFullscreenElement && e.top !== e && (i *= 100), 0 >= i || null == i) {
            if (i = N(t, n, s), (0 > i || null == i) && (i = t.style[n]), Ve.test(i)) return i;
            o = a && (ie.boxSizingReliable() || i === t.style[n]), i = parseFloat(i) || 0
        }
        return i + q(t, n, r || (a ? "border" : "content"), o, s) + "px"
    }

    function H(e, t, n, r, i) {
        return new H.prototype.init(e, t, n, r, i)
    }

    function F() {
        rt && (e.requestAnimationFrame(F), se.fx.tick())
    }

    function O() {
        return e.setTimeout(function () {
            nt = void 0
        }), nt = se.now()
    }

    function R(e, t) {
        var n, r = 0, i = {height: e};
        for (t = t ? 1 : 0; 4 > r; r += 2 - t) n = je[r], i["margin" + n] = i["padding" + n] = e;
        return t && (i.opacity = i.width = e), i
    }

    function P(e, t, n) {
        for (var r, i = (I.tweeners[t] || []).concat(I.tweeners["*"]), o = 0, s = i.length; s > o; o++) if (r = i[o].call(n, t, e)) return r
    }

    function M(e, t, n) {
        var r, i, o, s, a, u, l, c, f = "width" in t || "height" in t, d = this, h = {}, g = e.style,
            m = e.nodeType && Ae(e), v = Ce.get(e, "fxshow");
        n.queue || (s = se._queueHooks(e, "fx"), null == s.unqueued && (s.unqueued = 0, a = s.empty.fire, s.empty.fire = function () {
            s.unqueued || a()
        }), s.unqueued++, d.always(function () {
            d.always(function () {
                s.unqueued--, se.queue(e, "fx").length || s.empty.fire()
            })
        }));
        for (r in t) if (i = t[r], it.test(i)) {
            if (delete t[r], o = o || "toggle" === i, i === (m ? "hide" : "show")) {
                if ("show" !== i || !v || void 0 === v[r]) continue;
                m = !0
            }
            h[r] = v && v[r] || se.style(e, r)
        }
        if (u = !se.isEmptyObject(t), u || !se.isEmptyObject(h)) {
            f && 1 === e.nodeType && (n.overflow = [g.overflow, g.overflowX, g.overflowY], l = v && v.display, null == l && (l = Ce.get(e, "display")), c = se.css(e, "display"), "none" === c && (c = l || qe(e, {display: ""}, function () {
                return se.css(e, "display")
            })), ("inline" === c || "inline-block" === c && null != l) && "none" === se.css(e, "float") && (u || (d.done(function () {
                g.display = l
            }), null == l && (c = g.display, l = "none" === c ? "" : c)), g.display = "inline-block")), n.overflow && (g.overflow = "hidden", d.always(function () {
                g.overflow = n.overflow[0], g.overflowX = n.overflow[1], g.overflowY = n.overflow[2]
            })), u = !1;
            for (r in h) u || (v ? "hidden" in v && (m = v.hidden) : v = Ce.access(e, "fxshow", {display: l}), o && (v.hidden = !m), m && p([e], !0), d.done(function () {
                m || p([e]), Ce.remove(e, "fxshow");
                for (r in h) se.style(e, r, h[r])
            })), u = P(m ? v[r] : 0, r, d), r in v || (v[r] = u.start, m && (u.end = u.start, u.start = "width" === r || "height" === r ? 1 : 0))
        }
    }

    function W(e, t) {
        var n, r, i, o, s;
        for (n in e) if (r = se.camelCase(n), i = t[r], o = e[n], se.isArray(o) && (i = o[1], o = e[n] = o[0]), n !== r && (e[r] = o, delete e[n]), s = se.cssHooks[r], s && "expand" in s) {
            o = s.expand(o), delete e[r];
            for (n in o) n in e || (e[n] = o[n], t[n] = i)
        } else t[r] = i
    }

    function I(e, t, n) {
        var r, i, o = 0, s = I.prefilters.length, a = se.Deferred().always(function () {
            delete u.elem
        }), u = function () {
            if (i) return !1;
            for (var t = nt || O(), n = Math.max(0, l.startTime + l.duration - t), r = n / l.duration || 0, o = 1 - r, s = 0, u = l.tweens.length; u > s; s++) l.tweens[s].run(o);
            return a.notifyWith(e, [l, o, n]), 1 > o && u ? n : (a.resolveWith(e, [l]), !1)
        }, l = a.promise({
            elem: e,
            props: se.extend({}, t),
            opts: se.extend(!0, {specialEasing: {}, easing: se.easing._default}, n),
            originalProperties: t,
            originalOptions: n,
            startTime: nt || O(),
            duration: n.duration,
            tweens: [],
            createTween: function (t, n) {
                var r = se.Tween(e, l.opts, t, n, l.opts.specialEasing[t] || l.opts.easing);
                return l.tweens.push(r), r
            },
            stop: function (t) {
                var n = 0, r = t ? l.tweens.length : 0;
                if (i) return this;
                for (i = !0; r > n; n++) l.tweens[n].run(1);
                return t ? a.resolveWith(e, [l, t]) : a.rejectWith(e, [l, t]), this
            }
        }), c = l.props;
        for (W(c, l.opts.specialEasing); s > o; o++) if (r = I.prefilters[o].call(l, e, c, l.opts)) return se.isFunction(r.stop) && (se._queueHooks(l.elem, l.opts.queue).stop = se.proxy(r.stop, r)), r;
        return se.map(c, P, l), se.isFunction(l.opts.start) && l.opts.start.call(e, l), se.fx.timer(se.extend(u, {
            elem: e,
            anim: l,
            queue: l.opts.queue
        })), l.progress(l.opts.progress).done(l.opts.done, l.opts.complete).fail(l.opts.fail).always(l.opts.always)
    }

    function $(e) {
        return e.getAttribute && e.getAttribute("class") || ""
    }

    function B(e) {
        return function (t, n) {
            "string" != typeof t && (n = t, t = "*");
            var r, i = 0, o = t.toLowerCase().match(be) || [];
            if (se.isFunction(n)) for (; r = o[i++];) "+" === r[0] ? (r = r.slice(1) || "*", (e[r] = e[r] || []).unshift(n)) : (e[r] = e[r] || []).push(n)
        }
    }

    function _(e, t, n, r) {
        function i(a) {
            var u;
            return o[a] = !0, se.each(e[a] || [], function (e, a) {
                var l = a(t, n, r);
                return "string" != typeof l || s || o[l] ? s ? !(u = l) : void 0 : (t.dataTypes.unshift(l), i(l), !1)
            }), u
        }

        var o = {}, s = e === wt;
        return i(t.dataTypes[0]) || !o["*"] && i("*")
    }

    function X(e, t) {
        var n, r, i = se.ajaxSettings.flatOptions || {};
        for (n in t) void 0 !== t[n] && ((i[n] ? e : r || (r = {}))[n] = t[n]);
        return r && se.extend(!0, e, r), e
    }

    function z(e, t, n) {
        for (var r, i, o, s, a = e.contents, u = e.dataTypes; "*" === u[0];) u.shift(), void 0 === r && (r = e.mimeType || t.getResponseHeader("Content-Type"));
        if (r) for (i in a) if (a[i] && a[i].test(r)) {
            u.unshift(i);
            break
        }
        if (u[0] in n) o = u[0]; else {
            for (i in n) {
                if (!u[0] || e.converters[i + " " + u[0]]) {
                    o = i;
                    break
                }
                s || (s = i)
            }
            o = o || s
        }
        return o ? (o !== u[0] && u.unshift(o), n[o]) : void 0
    }

    function U(e, t, n, r) {
        var i, o, s, a, u, l = {}, c = e.dataTypes.slice();
        if (c[1]) for (s in e.converters) l[s.toLowerCase()] = e.converters[s];
        for (o = c.shift(); o;) if (e.responseFields[o] && (n[e.responseFields[o]] = t), !u && r && e.dataFilter && (t = e.dataFilter(t, e.dataType)), u = o, o = c.shift()) if ("*" === o) o = u; else if ("*" !== u && u !== o) {
            if (s = l[u + " " + o] || l["* " + o], !s) for (i in l) if (a = i.split(" "), a[1] === o && (s = l[u + " " + a[0]] || l["* " + a[0]])) {
                s === !0 ? s = l[i] : l[i] !== !0 && (o = a[0], c.unshift(a[1]));
                break
            }
            if (s !== !0) if (s && e["throws"]) t = s(t); else try {
                t = s(t)
            } catch (f) {
                return {state: "parsererror", error: s ? f : "No conversion from " + u + " to " + o}
            }
        }
        return {state: "success", data: t}
    }

    function V(e, t, n, r) {
        var i;
        if (se.isArray(t)) se.each(t, function (t, i) {
            n || Et.test(e) ? r(e, i) : V(e + "[" + ("object" == typeof i ? t : "") + "]", i, n, r)
        }); else if (n || "object" !== se.type(t)) r(e, t); else for (i in t) V(e + "[" + i + "]", t[i], n, r)
    }

    function Y(e) {
        return se.isWindow(e) ? e : 9 === e.nodeType && e.defaultView
    }

    var G = [], Q = e.document, J = G.slice, K = G.concat, Z = G.push, ee = G.indexOf, te = {}, ne = te.toString,
        re = te.hasOwnProperty, ie = {}, oe = "3.0.0-alpha1", se = function (e, t) {
            return new se.fn.init(e, t)
        }, ae = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, ue = /^-ms-/, le = /-([a-z])/g, ce = function (e, t) {
            return t.toUpperCase()
        };
    se.fn = se.prototype = {
        jquery: oe, constructor: se, length: 0, toArray: function () {
            return J.call(this)
        }, get: function (e) {
            return null != e ? 0 > e ? this[e + this.length] : this[e] : J.call(this)
        }, pushStack: function (e) {
            var t = se.merge(this.constructor(), e);
            return t.prevObject = this, t
        }, each: function (e) {
            return se.each(this, e)
        }, map: function (e) {
            return this.pushStack(se.map(this, function (t, n) {
                return e.call(t, n, t)
            }))
        }, slice: function () {
            return this.pushStack(J.apply(this, arguments))
        }, first: function () {
            return this.eq(0)
        }, last: function () {
            return this.eq(-1)
        }, eq: function (e) {
            var t = this.length, n = +e + (0 > e ? t : 0);
            return this.pushStack(n >= 0 && t > n ? [this[n]] : [])
        }, end: function () {
            return this.prevObject || this.constructor(null)
        }, push: Z, sort: G.sort, splice: G.splice
    }, se.extend = se.fn.extend = function () {
        var e, t, n, r, i, o, s = arguments[0] || {}, a = 1, u = arguments.length, l = !1;
        for ("boolean" == typeof s && (l = s, s = arguments[a] || {}, a++), "object" == typeof s || se.isFunction(s) || (s = {}), a === u && (s = this, a--); u > a; a++) if (null != (e = arguments[a])) for (t in e) n = s[t], r = e[t], s !== r && (l && r && (se.isPlainObject(r) || (i = se.isArray(r))) ? (i ? (i = !1, o = n && se.isArray(n) ? n : []) : o = n && se.isPlainObject(n) ? n : {}, s[t] = se.extend(l, o, r)) : void 0 !== r && (s[t] = r));
        return s
    }, se.extend({
        expando: "jQuery" + (oe + Math.random()).replace(/\D/g, ""), isReady: !0, error: function (e) {
            throw new Error(e)
        }, noop: function () {
        }, isFunction: function (e) {
            return "function" === se.type(e)
        }, isArray: Array.isArray, isWindow: function (e) {
            return null != e && e === e.window
        }, isNumeric: function (e) {
            return !se.isArray(e) && e - parseFloat(e) + 1 >= 0
        }, isPlainObject: function (e) {
            return "object" !== se.type(e) || e.nodeType || se.isWindow(e) ? !1 : !e.constructor || re.call(e.constructor.prototype, "isPrototypeOf")
        }, isEmptyObject: function (e) {
            var t;
            for (t in e) return !1;
            return !0
        }, type: function (e) {
            return null == e ? e + "" : "object" == typeof e || "function" == typeof e ? te[ne.call(e)] || "object" : typeof e
        }, globalEval: function (e) {
            var t = Q.createElement("script");
            t.text = e, Q.head.appendChild(t).parentNode.removeChild(t)
        }, camelCase: function (e) {
            return e.replace(ue, "ms-").replace(le, ce)
        }, nodeName: function (e, t) {
            return e.nodeName && e.nodeName.toLowerCase() === t.toLowerCase()
        }, each: function (e, t) {
            var r = 0, i = e.length, o = n(e);
            if (o) for (; i > r && t.call(e[r], r, e[r]) !== !1; r++) ; else for (r in e) if (t.call(e[r], r, e[r]) === !1) break;
            return e
        }, trim: function (e) {
            return null == e ? "" : (e + "").replace(ae, "")
        }, makeArray: function (e, t) {
            var r = t || [];
            return null != e && (n(Object(e)) ? se.merge(r, "string" == typeof e ? [e] : e) : Z.call(r, e)), r
        }, inArray: function (e, t, n) {
            return null == t ? -1 : ee.call(t, e, n)
        }, merge: function (e, t) {
            for (var n = +t.length, r = 0, i = e.length; n > r; r++) e[i++] = t[r];
            return e.length = i, e
        }, grep: function (e, t, n) {
            for (var r, i = [], o = 0, s = e.length, a = !n; s > o; o++) r = !t(e[o], o), r !== a && i.push(e[o]);
            return i
        }, map: function (e, t, r) {
            var i, o = 0, s = e.length, a = n(e), u = [];
            if (a) for (; s > o; o++) i = t(e[o], o, r), null != i && u.push(i); else for (o in e) i = t(e[o], o, r), null != i && u.push(i);
            return K.apply([], u)
        }, guid: 1, proxy: function (e, t) {
            var n, r, i;
            return "string" == typeof t && (n = e[t], t = e, e = n), se.isFunction(e) ? (r = J.call(arguments, 2), i = function () {
                return e.apply(t || this, r.concat(J.call(arguments)))
            }, i.guid = e.guid = e.guid || se.guid++, i) : void 0
        }, now: Date.now, support: ie
    }), "function" == typeof Symbol && (se.fn[Symbol.iterator] = G[Symbol.iterator]), se.each("Boolean Number String Function Array Date RegExp Object Error".split(" "), function (e, t) {
        te["[object " + t + "]"] = t.toLowerCase()
    });
    var fe = function (e) {
        function t(e, t, n, r) {
            var i, o, s, a, u, l, f, d = t && t.ownerDocument, h = t ? t.nodeType : 9;
            if (n = n || [], "string" != typeof e || !e || 1 !== h && 9 !== h && 11 !== h) return n;
            if (!r && ((t ? t.ownerDocument || t : I) !== L && q(t), t = t || L, F)) {
                if (11 !== h && (u = ve.exec(e))) if (i = u[1]) {
                    if (9 === h) {
                        if (!(s = t.getElementById(i))) return n;
                        if (s.id === i) return n.push(s), n
                    } else if (d && (s = d.getElementById(i)) && M(t, s) && s.id === i) return n.push(s), n
                } else {
                    if (u[2]) return K.apply(n, t.getElementsByTagName(e)), n;
                    if ((i = u[3]) && w.getElementsByClassName && t.getElementsByClassName) return K.apply(n, t.getElementsByClassName(i)), n
                }
                if (w.qsa && !z[e + " "] && (!O || !O.test(e))) {
                    if (1 !== h) d = t, f = e; else if ("object" !== t.nodeName.toLowerCase()) {
                        for ((a = t.getAttribute("id")) ? a = a.replace(xe, "\\$&") : t.setAttribute("id", a = W), l = E(e), o = l.length; o--;) l[o] = "[id='" + a + "'] " + p(l[o]);
                        f = l.join(","), d = ye.test(e) && c(t.parentNode) || t
                    }
                    if (f) try {
                        return K.apply(n, d.querySelectorAll(f)), n
                    } catch (g) {
                    } finally {
                        a === W && t.removeAttribute("id")
                    }
                }
            }
            return N(e.replace(ae, "$1"), t, n, r)
        }

        function n() {
            function e(n, r) {
                return t.push(n + " ") > T.cacheLength && delete e[t.shift()], e[n + " "] = r
            }

            var t = [];
            return e
        }

        function r(e) {
            return e[W] = !0, e
        }

        function i(e) {
            var t = L.createElement("div");
            try {
                return !!e(t)
            } catch (n) {
                return !1
            } finally {
                t.parentNode && t.parentNode.removeChild(t), t = null
            }
        }

        function o(e, t) {
            for (var n = e.split("|"), r = e.length; r--;) T.attrHandle[n[r]] = t
        }

        function s(e, t) {
            var n = t && e,
                r = n && 1 === e.nodeType && 1 === t.nodeType && (~t.sourceIndex || V) - (~e.sourceIndex || V);
            if (r) return r;
            if (n) for (; n = n.nextSibling;) if (n === t) return -1;
            return e ? 1 : -1
        }

        function a(e) {
            return function (t) {
                var n = t.nodeName.toLowerCase();
                return "input" === n && t.type === e
            }
        }

        function u(e) {
            return function (t) {
                var n = t.nodeName.toLowerCase();
                return ("input" === n || "button" === n) && t.type === e
            }
        }

        function l(e) {
            return r(function (t) {
                return t = +t, r(function (n, r) {
                    for (var i, o = e([], n.length, t), s = o.length; s--;) n[i = o[s]] && (n[i] = !(r[i] = n[i]))
                })
            })
        }

        function c(e) {
            return e && "undefined" != typeof e.getElementsByTagName && e
        }

        function f() {
        }

        function p(e) {
            for (var t = 0, n = e.length, r = ""; n > t; t++) r += e[t].value;
            return r
        }

        function d(e, t, n) {
            var r = t.dir, i = n && "parentNode" === r, o = B++;
            return t.first ? function (t, n, o) {
                for (; t = t[r];) if (1 === t.nodeType || i) return e(t, n, o)
            } : function (t, n, s) {
                var a, u, l, c = [$, o];
                if (s) {
                    for (; t = t[r];) if ((1 === t.nodeType || i) && e(t, n, s)) return !0
                } else for (; t = t[r];) if (1 === t.nodeType || i) {
                    if (l = t[W] || (t[W] = {}), u = l[t.uniqueID] || (l[t.uniqueID] = {}), (a = u[r]) && a[0] === $ && a[1] === o) return c[2] = a[2];
                    if (u[r] = c, c[2] = e(t, n, s)) return !0
                }
            }
        }

        function h(e) {
            return e.length > 1 ? function (t, n, r) {
                for (var i = e.length; i--;) if (!e[i](t, n, r)) return !1;
                return !0
            } : e[0]
        }

        function g(e, n, r) {
            for (var i = 0, o = n.length; o > i; i++) t(e, n[i], r);
            return r
        }

        function m(e, t, n, r, i) {
            for (var o, s = [], a = 0, u = e.length, l = null != t; u > a; a++) (o = e[a]) && (n && !n(o, r, i) || (s.push(o), l && t.push(a)));
            return s
        }

        function v(e, t, n, i, o, s) {
            return i && !i[W] && (i = v(i)), o && !o[W] && (o = v(o, s)), r(function (r, s, a, u) {
                var l, c, f, p = [], d = [], h = s.length, v = r || g(t || "*", a.nodeType ? [a] : a, []),
                    y = !e || !r && t ? v : m(v, p, e, a, u), x = n ? o || (r ? e : h || i) ? [] : s : y;
                if (n && n(y, x, a, u), i) for (l = m(x, d), i(l, [], a, u), c = l.length; c--;) (f = l[c]) && (x[d[c]] = !(y[d[c]] = f));
                if (r) {
                    if (o || e) {
                        if (o) {
                            for (l = [], c = x.length; c--;) (f = x[c]) && l.push(y[c] = f);
                            o(null, x = [], l, u)
                        }
                        for (c = x.length; c--;) (f = x[c]) && (l = o ? ee(r, f) : p[c]) > -1 && (r[l] = !(s[l] = f))
                    }
                } else x = m(x === s ? x.splice(h, x.length) : x), o ? o(null, s, x, u) : K.apply(s, x)
            })
        }

        function y(e) {
            for (var t, n, r, i = e.length, o = T.relative[e[0].type], s = o || T.relative[" "], a = o ? 1 : 0, u = d(function (e) {
                return e === t
            }, s, !0), l = d(function (e) {
                return ee(t, e) > -1
            }, s, !0), c = [function (e, n, r) {
                var i = !o && (r || n !== S) || ((t = n).nodeType ? u(e, n, r) : l(e, n, r));
                return t = null, i
            }]; i > a; a++) if (n = T.relative[e[a].type]) c = [d(h(c), n)]; else {
                if (n = T.filter[e[a].type].apply(null, e[a].matches), n[W]) {
                    for (r = ++a; i > r && !T.relative[e[r].type]; r++) ;
                    return v(a > 1 && h(c), a > 1 && p(e.slice(0, a - 1).concat({value: " " === e[a - 2].type ? "*" : ""})).replace(ae, "$1"), n, r > a && y(e.slice(a, r)), i > r && y(e = e.slice(r)), i > r && p(e))
                }
                c.push(n)
            }
            return h(c)
        }

        function x(e, n) {
            var i = n.length > 0, o = e.length > 0, s = function (r, s, a, u, l) {
                var c, f, p, d = 0, h = "0", g = r && [], v = [], y = S, x = r || o && T.find.TAG("*", l),
                    b = $ += null == y ? 1 : Math.random() || .1, w = x.length;
                for (l && (S = s === L || s || l); h !== w && null != (c = x[h]); h++) {
                    if (o && c) {
                        for (f = 0, s || c.ownerDocument === L || (q(c), a = !F); p = e[f++];) if (p(c, s || L, a)) {
                            u.push(c);
                            break
                        }
                        l && ($ = b)
                    }
                    i && ((c = !p && c) && d--, r && g.push(c))
                }
                if (d += h, i && h !== d) {
                    for (f = 0; p = n[f++];) p(g, v, s, a);
                    if (r) {
                        if (d > 0) for (; h--;) g[h] || v[h] || (v[h] = Q.call(u));
                        v = m(v)
                    }
                    K.apply(u, v), l && !r && v.length > 0 && d + n.length > 1 && t.uniqueSort(u)
                }
                return l && ($ = b, S = y), g
            };
            return i ? r(s) : s
        }

        var b, w, T, C, k, E, D, N, S, j, A, q, L, H, F, O, R, P, M, W = "sizzle" + 1 * new Date, I = e.document, $ = 0,
            B = 0, _ = n(), X = n(), z = n(), U = function (e, t) {
                return e === t && (A = !0), 0
            }, V = 1 << 31, Y = {}.hasOwnProperty, G = [], Q = G.pop, J = G.push, K = G.push, Z = G.slice,
            ee = function (e, t) {
                for (var n = 0, r = e.length; r > n; n++) if (e[n] === t) return n;
                return -1
            },
            te = "checked|selected|async|autofocus|autoplay|controls|defer|disabled|hidden|ismap|loop|multiple|open|readonly|required|scoped",
            ne = "[\\x20\\t\\r\\n\\f]", re = "(?:\\\\.|[\\w-]|[^\\x00-\\xa0])+",
            ie = "\\[" + ne + "*(" + re + ")(?:" + ne + "*([*^$|!~]?=)" + ne + "*(?:'((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\"|(" + re + "))|)" + ne + "*\\]",
            oe = ":(" + re + ")(?:\\((('((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\")|((?:\\\\.|[^\\\\()[\\]]|" + ie + ")*)|.*)\\)|)",
            se = new RegExp(ne + "+", "g"), ae = new RegExp("^" + ne + "+|((?:^|[^\\\\])(?:\\\\.)*)" + ne + "+$", "g"),
            ue = new RegExp("^" + ne + "*," + ne + "*"), le = new RegExp("^" + ne + "*([>+~]|" + ne + ")" + ne + "*"),
            ce = new RegExp("=" + ne + "*([^\\]'\"]*?)" + ne + "*\\]", "g"), fe = new RegExp(oe),
            pe = new RegExp("^" + re + "$"), de = {
                ID: new RegExp("^#(" + re + ")"),
                CLASS: new RegExp("^\\.(" + re + ")"),
                TAG: new RegExp("^(" + re + "|[*])"),
                ATTR: new RegExp("^" + ie),
                PSEUDO: new RegExp("^" + oe),
                CHILD: new RegExp("^:(only|first|last|nth|nth-last)-(child|of-type)(?:\\(" + ne + "*(even|odd|(([+-]|)(\\d*)n|)" + ne + "*(?:([+-]|)" + ne + "*(\\d+)|))" + ne + "*\\)|)", "i"),
                bool: new RegExp("^(?:" + te + ")$", "i"),
                needsContext: new RegExp("^" + ne + "*[>+~]|:(even|odd|eq|gt|lt|nth|first|last)(?:\\(" + ne + "*((?:-\\d)?\\d*)" + ne + "*\\)|)(?=[^-]|$)", "i")
            }, he = /^(?:input|select|textarea|button)$/i, ge = /^h\d$/i, me = /^[^{]+\{\s*\[native \w/,
            ve = /^(?:#([\w-]+)|(\w+)|\.([\w-]+))$/, ye = /[+~]/, xe = /'|\\/g,
            be = new RegExp("\\\\([\\da-f]{1,6}" + ne + "?|(" + ne + ")|.)", "ig"), we = function (e, t, n) {
                var r = "0x" + t - 65536;
                return r !== r || n ? t : 0 > r ? String.fromCharCode(r + 65536) : String.fromCharCode(r >> 10 | 55296, 1023 & r | 56320)
            }, Te = function () {
                q()
            };
        try {
            K.apply(G = Z.call(I.childNodes), I.childNodes), G[I.childNodes.length].nodeType
        } catch (Ce) {
            K = {
                apply: G.length ? function (e, t) {
                    J.apply(e, Z.call(t))
                } : function (e, t) {
                    for (var n = e.length, r = 0; e[n++] = t[r++];) ;
                    e.length = n - 1
                }
            }
        }
        w = t.support = {}, k = t.isXML = function (e) {
            var t = e && (e.ownerDocument || e).documentElement;
            return t ? "HTML" !== t.nodeName : !1
        }, q = t.setDocument = function (e) {
            var t, n, r = e ? e.ownerDocument || e : I;
            return r !== L && 9 === r.nodeType && r.documentElement ? (L = r, H = L.documentElement, F = !k(L), L.documentMode && (n = L.defaultView) && n.top !== n && (n.addEventListener ? n.addEventListener("unload", Te, !1) : n.attachEvent && n.attachEvent("onunload", Te)), w.attributes = i(function (e) {
                return e.className = "i", !e.getAttribute("className")
            }), w.getElementsByTagName = i(function (e) {
                return e.appendChild(L.createComment("")), !e.getElementsByTagName("*").length
            }), w.getElementsByClassName = me.test(L.getElementsByClassName), w.getById = i(function (e) {
                return H.appendChild(e).id = W, !L.getElementsByName || !L.getElementsByName(W).length
            }), w.getById ? (T.find.ID = function (e, t) {
                if ("undefined" != typeof t.getElementById && F) {
                    var n = t.getElementById(e);
                    return n ? [n] : []
                }
            }, T.filter.ID = function (e) {
                var t = e.replace(be, we);
                return function (e) {
                    return e.getAttribute("id") === t
                }
            }) : (delete T.find.ID, T.filter.ID = function (e) {
                var t = e.replace(be, we);
                return function (e) {
                    var n = "undefined" != typeof e.getAttributeNode && e.getAttributeNode("id");
                    return n && n.value === t
                }
            }), T.find.TAG = w.getElementsByTagName ? function (e, t) {
                return "undefined" != typeof t.getElementsByTagName ? t.getElementsByTagName(e) : w.qsa ? t.querySelectorAll(e) : void 0
            } : function (e, t) {
                var n, r = [], i = 0, o = t.getElementsByTagName(e);
                if ("*" === e) {
                    for (; n = o[i++];) 1 === n.nodeType && r.push(n);
                    return r
                }
                return o
            }, T.find.CLASS = w.getElementsByClassName && function (e, t) {
                return "undefined" != typeof t.getElementsByClassName && F ? t.getElementsByClassName(e) : void 0
            }, R = [], O = [], (w.qsa = me.test(L.querySelectorAll)) && (i(function (e) {
                H.appendChild(e).innerHTML = "<a id='" + W + "'></a><select id='" + W + "-\r\\' msallowcapture=''><option selected=''></option></select>", e.querySelectorAll("[msallowcapture^='']").length && O.push("[*^$]=" + ne + "*(?:''|\"\")"), e.querySelectorAll("[selected]").length || O.push("\\[" + ne + "*(?:value|" + te + ")"), e.querySelectorAll("[id~=" + W + "-]").length || O.push("~="), e.querySelectorAll(":checked").length || O.push(":checked"), e.querySelectorAll("a#" + W + "+*").length || O.push(".#.+[+~]")
            }), i(function (e) {
                var t = L.createElement("input");
                t.setAttribute("type", "hidden"), e.appendChild(t).setAttribute("name", "D"), e.querySelectorAll("[name=d]").length && O.push("name" + ne + "*[*^$|!~]?="), e.querySelectorAll(":enabled").length || O.push(":enabled", ":disabled"), e.querySelectorAll("*,:x"), O.push(",.*:")
            })), (w.matchesSelector = me.test(P = H.matches || H.webkitMatchesSelector || H.mozMatchesSelector || H.oMatchesSelector || H.msMatchesSelector)) && i(function (e) {
                w.disconnectedMatch = P.call(e, "div"), P.call(e, "[s!='']:x"), R.push("!=", oe)
            }), O = O.length && new RegExp(O.join("|")), R = R.length && new RegExp(R.join("|")), t = me.test(H.compareDocumentPosition), M = t || me.test(H.contains) ? function (e, t) {
                var n = 9 === e.nodeType ? e.documentElement : e, r = t && t.parentNode;
                return e === r || !(!r || 1 !== r.nodeType || !(n.contains ? n.contains(r) : e.compareDocumentPosition && 16 & e.compareDocumentPosition(r)))
            } : function (e, t) {
                if (t) for (; t = t.parentNode;) if (t === e) return !0;
                return !1
            }, U = t ? function (e, t) {
                if (e === t) return A = !0, 0;
                var n = !e.compareDocumentPosition - !t.compareDocumentPosition;
                return n ? n : (n = (e.ownerDocument || e) === (t.ownerDocument || t) ? e.compareDocumentPosition(t) : 1, 1 & n || !w.sortDetached && t.compareDocumentPosition(e) === n ? e === L || e.ownerDocument === I && M(I, e) ? -1 : t === L || t.ownerDocument === I && M(I, t) ? 1 : j ? ee(j, e) - ee(j, t) : 0 : 4 & n ? -1 : 1)
            } : function (e, t) {
                if (e === t) return A = !0, 0;
                var n, r = 0, i = e.parentNode, o = t.parentNode, a = [e], u = [t];
                if (!i || !o) return e === L ? -1 : t === L ? 1 : i ? -1 : o ? 1 : j ? ee(j, e) - ee(j, t) : 0;
                if (i === o) return s(e, t);
                for (n = e; n = n.parentNode;) a.unshift(n);
                for (n = t; n = n.parentNode;) u.unshift(n);
                for (; a[r] === u[r];) r++;
                return r ? s(a[r], u[r]) : a[r] === I ? -1 : u[r] === I ? 1 : 0
            }, L) : L
        }, t.matches = function (e, n) {
            return t(e, null, null, n)
        }, t.matchesSelector = function (e, n) {
            if ((e.ownerDocument || e) !== L && q(e), n = n.replace(ce, "='$1']"), w.matchesSelector && F && !z[n + " "] && (!R || !R.test(n)) && (!O || !O.test(n))) try {
                var r = P.call(e, n);
                if (r || w.disconnectedMatch || e.document && 11 !== e.document.nodeType) return r
            } catch (i) {
            }
            return t(n, L, null, [e]).length > 0
        }, t.contains = function (e, t) {
            return (e.ownerDocument || e) !== L && q(e), M(e, t)
        }, t.attr = function (e, t) {
            (e.ownerDocument || e) !== L && q(e);
            var n = T.attrHandle[t.toLowerCase()],
                r = n && Y.call(T.attrHandle, t.toLowerCase()) ? n(e, t, !F) : void 0;
            return void 0 !== r ? r : w.attributes || !F ? e.getAttribute(t) : (r = e.getAttributeNode(t)) && r.specified ? r.value : null
        }, t.error = function (e) {
            throw new Error("Syntax error, unrecognized expression: " + e)
        }, t.uniqueSort = function (e) {
            var t, n = [], r = 0, i = 0;
            if (A = !w.detectDuplicates, j = !w.sortStable && e.slice(0), e.sort(U), A) {
                for (; t = e[i++];) t === e[i] && (r = n.push(i));
                for (; r--;) e.splice(n[r], 1)
            }
            return j = null, e
        }, C = t.getText = function (e) {
            var t, n = "", r = 0, i = e.nodeType;
            if (i) {
                if (1 === i || 9 === i || 11 === i) {
                    if ("string" == typeof e.textContent) return e.textContent;
                    for (e = e.firstChild; e; e = e.nextSibling) n += C(e)
                } else if (3 === i || 4 === i) return e.nodeValue
            } else for (; t = e[r++];) n += C(t);
            return n
        }, T = t.selectors = {
            cacheLength: 50,
            createPseudo: r,
            match: de,
            attrHandle: {},
            find: {},
            relative: {
                ">": {dir: "parentNode", first: !0},
                " ": {dir: "parentNode"},
                "+": {dir: "previousSibling", first: !0},
                "~": {dir: "previousSibling"}
            },
            preFilter: {
                ATTR: function (e) {
                    return e[1] = e[1].replace(be, we), e[3] = (e[3] || e[4] || e[5] || "").replace(be, we), "~=" === e[2] && (e[3] = " " + e[3] + " "), e.slice(0, 4)
                }, CHILD: function (e) {
                    return e[1] = e[1].toLowerCase(), "nth" === e[1].slice(0, 3) ? (e[3] || t.error(e[0]), e[4] = +(e[4] ? e[5] + (e[6] || 1) : 2 * ("even" === e[3] || "odd" === e[3])), e[5] = +(e[7] + e[8] || "odd" === e[3])) : e[3] && t.error(e[0]), e
                }, PSEUDO: function (e) {
                    var t, n = !e[6] && e[2];
                    return de.CHILD.test(e[0]) ? null : (e[3] ? e[2] = e[4] || e[5] || "" : n && fe.test(n) && (t = E(n, !0)) && (t = n.indexOf(")", n.length - t) - n.length) && (e[0] = e[0].slice(0, t), e[2] = n.slice(0, t)), e.slice(0, 3))
                }
            },
            filter: {
                TAG: function (e) {
                    var t = e.replace(be, we).toLowerCase();
                    return "*" === e ? function () {
                        return !0
                    } : function (e) {
                        return e.nodeName && e.nodeName.toLowerCase() === t
                    }
                }, CLASS: function (e) {
                    var t = _[e + " "];
                    return t || (t = new RegExp("(^|" + ne + ")" + e + "(" + ne + "|$)")) && _(e, function (e) {
                        return t.test("string" == typeof e.className && e.className || "undefined" != typeof e.getAttribute && e.getAttribute("class") || "")
                    })
                }, ATTR: function (e, n, r) {
                    return function (i) {
                        var o = t.attr(i, e);
                        return null == o ? "!=" === n : n ? (o += "", "=" === n ? o === r : "!=" === n ? o !== r : "^=" === n ? r && 0 === o.indexOf(r) : "*=" === n ? r && o.indexOf(r) > -1 : "$=" === n ? r && o.slice(-r.length) === r : "~=" === n ? (" " + o.replace(se, " ") + " ").indexOf(r) > -1 : "|=" === n ? o === r || o.slice(0, r.length + 1) === r + "-" : !1) : !0
                    }
                }, CHILD: function (e, t, n, r, i) {
                    var o = "nth" !== e.slice(0, 3), s = "last" !== e.slice(-4), a = "of-type" === t;
                    return 1 === r && 0 === i ? function (e) {
                        return !!e.parentNode
                    } : function (t, n, u) {
                        var l, c, f, p, d, h, g = o !== s ? "nextSibling" : "previousSibling", m = t.parentNode,
                            v = a && t.nodeName.toLowerCase(), y = !u && !a, x = !1;
                        if (m) {
                            if (o) {
                                for (; g;) {
                                    for (p = t; p = p[g];) if (a ? p.nodeName.toLowerCase() === v : 1 === p.nodeType) return !1;
                                    h = g = "only" === e && !h && "nextSibling"
                                }
                                return !0
                            }
                            if (h = [s ? m.firstChild : m.lastChild], s && y) {
                                for (p = m, f = p[W] || (p[W] = {}), c = f[p.uniqueID] || (f[p.uniqueID] = {}), l = c[e] || [], d = l[0] === $ && l[1], x = d && l[2], p = d && m.childNodes[d]; p = ++d && p && p[g] || (x = d = 0) || h.pop();) if (1 === p.nodeType && ++x && p === t) {
                                    c[e] = [$, d, x];
                                    break
                                }
                            } else if (y && (p = t, f = p[W] || (p[W] = {}), c = f[p.uniqueID] || (f[p.uniqueID] = {}), l = c[e] || [], d = l[0] === $ && l[1], x = d), x === !1) for (; (p = ++d && p && p[g] || (x = d = 0) || h.pop()) && ((a ? p.nodeName.toLowerCase() !== v : 1 !== p.nodeType) || !++x || (y && (f = p[W] || (p[W] = {}), c = f[p.uniqueID] || (f[p.uniqueID] = {}), c[e] = [$, x]), p !== t));) ;
                            return x -= i, x === r || x % r === 0 && x / r >= 0
                        }
                    }
                }, PSEUDO: function (e, n) {
                    var i, o = T.pseudos[e] || T.setFilters[e.toLowerCase()] || t.error("unsupported pseudo: " + e);
                    return o[W] ? o(n) : o.length > 1 ? (i = [e, e, "", n], T.setFilters.hasOwnProperty(e.toLowerCase()) ? r(function (e, t) {
                        for (var r, i = o(e, n), s = i.length; s--;) r = ee(e, i[s]), e[r] = !(t[r] = i[s])
                    }) : function (e) {
                        return o(e, 0, i)
                    }) : o
                }
            },
            pseudos: {
                not: r(function (e) {
                    var t = [], n = [], i = D(e.replace(ae, "$1"));
                    return i[W] ? r(function (e, t, n, r) {
                        for (var o, s = i(e, null, r, []), a = e.length; a--;) (o = s[a]) && (e[a] = !(t[a] = o))
                    }) : function (e, r, o) {
                        return t[0] = e, i(t, null, o, n), t[0] = null, !n.pop()
                    }
                }), has: r(function (e) {
                    return function (n) {
                        return t(e, n).length > 0
                    }
                }), contains: r(function (e) {
                    return e = e.replace(be, we), function (t) {
                        return (t.textContent || t.innerText || C(t)).indexOf(e) > -1
                    }
                }), lang: r(function (e) {
                    return pe.test(e || "") || t.error("unsupported lang: " + e), e = e.replace(be, we).toLowerCase(), function (t) {
                        var n;
                        do if (n = F ? t.lang : t.getAttribute("xml:lang") || t.getAttribute("lang")) return n = n.toLowerCase(), n === e || 0 === n.indexOf(e + "-"); while ((t = t.parentNode) && 1 === t.nodeType);
                        return !1
                    }
                }), target: function (t) {
                    var n = e.location && e.location.hash;
                    return n && n.slice(1) === t.id
                }, root: function (e) {
                    return e === H
                }, focus: function (e) {
                    return e === L.activeElement && (!L.hasFocus || L.hasFocus()) && !!(e.type || e.href || ~e.tabIndex)
                }, enabled: function (e) {
                    return e.disabled === !1
                }, disabled: function (e) {
                    return e.disabled === !0
                }, checked: function (e) {
                    var t = e.nodeName.toLowerCase();
                    return "input" === t && !!e.checked || "option" === t && !!e.selected
                }, selected: function (e) {
                    return e.parentNode && e.parentNode.selectedIndex, e.selected === !0
                }, empty: function (e) {
                    for (e = e.firstChild; e; e = e.nextSibling) if (e.nodeType < 6) return !1;
                    return !0
                }, parent: function (e) {
                    return !T.pseudos.empty(e)
                }, header: function (e) {
                    return ge.test(e.nodeName)
                }, input: function (e) {
                    return he.test(e.nodeName)
                }, button: function (e) {
                    var t = e.nodeName.toLowerCase();
                    return "input" === t && "button" === e.type || "button" === t
                }, text: function (e) {
                    var t;
                    return "input" === e.nodeName.toLowerCase() && "text" === e.type && (null == (t = e.getAttribute("type")) || "text" === t.toLowerCase())
                }, first: l(function () {
                    return [0]
                }), last: l(function (e, t) {
                    return [t - 1]
                }), eq: l(function (e, t, n) {
                    return [0 > n ? n + t : n]
                }), even: l(function (e, t) {
                    for (var n = 0; t > n; n += 2) e.push(n);
                    return e
                }), odd: l(function (e, t) {
                    for (var n = 1; t > n; n += 2) e.push(n);
                    return e
                }), lt: l(function (e, t, n) {
                    for (var r = 0 > n ? n + t : n; --r >= 0;) e.push(r);
                    return e
                }), gt: l(function (e, t, n) {
                    for (var r = 0 > n ? n + t : n; ++r < t;) e.push(r);
                    return e
                })
            }
        }, T.pseudos.nth = T.pseudos.eq;
        for (b in{radio: !0, checkbox: !0, file: !0, password: !0, image: !0}) T.pseudos[b] = a(b);
        for (b in{submit: !0, reset: !0}) T.pseudos[b] = u(b);
        return f.prototype = T.filters = T.pseudos, T.setFilters = new f, E = t.tokenize = function (e, n) {
            var r, i, o, s, a, u, l, c = X[e + " "];
            if (c) return n ? 0 : c.slice(0);
            for (a = e, u = [], l = T.preFilter; a;) {
                r && !(i = ue.exec(a)) || (i && (a = a.slice(i[0].length) || a), u.push(o = [])), r = !1,
                (i = le.exec(a)) && (r = i.shift(), o.push({
                    value: r,
                    type: i[0].replace(ae, " ")
                }), a = a.slice(r.length));
                for (s in T.filter) !(i = de[s].exec(a)) || l[s] && !(i = l[s](i)) || (r = i.shift(), o.push({
                    value: r,
                    type: s,
                    matches: i
                }), a = a.slice(r.length));
                if (!r) break
            }
            return n ? a.length : a ? t.error(e) : X(e, u).slice(0)
        }, D = t.compile = function (e, t) {
            var n, r = [], i = [], o = z[e + " "];
            if (!o) {
                for (t || (t = E(e)), n = t.length; n--;) o = y(t[n]), o[W] ? r.push(o) : i.push(o);
                o = z(e, x(i, r)), o.selector = e
            }
            return o
        }, N = t.select = function (e, t, n, r) {
            var i, o, s, a, u, l = "function" == typeof e && e, f = !r && E(e = l.selector || e);
            if (n = n || [], 1 === f.length) {
                if (o = f[0] = f[0].slice(0), o.length > 2 && "ID" === (s = o[0]).type && w.getById && 9 === t.nodeType && F && T.relative[o[1].type]) {
                    if (t = (T.find.ID(s.matches[0].replace(be, we), t) || [])[0], !t) return n;
                    l && (t = t.parentNode), e = e.slice(o.shift().value.length)
                }
                for (i = de.needsContext.test(e) ? 0 : o.length; i-- && (s = o[i], !T.relative[a = s.type]);) if ((u = T.find[a]) && (r = u(s.matches[0].replace(be, we), ye.test(o[0].type) && c(t.parentNode) || t))) {
                    if (o.splice(i, 1), e = r.length && p(o), !e) return K.apply(n, r), n;
                    break
                }
            }
            return (l || D(e, f))(r, t, !F, n, !t || ye.test(e) && c(t.parentNode) || t), n
        }, w.sortStable = W.split("").sort(U).join("") === W, w.detectDuplicates = !!A, q(), w.sortDetached = i(function (e) {
            return 1 & e.compareDocumentPosition(L.createElement("div"))
        }), i(function (e) {
            return e.innerHTML = "<a href='#'></a>", "#" === e.firstChild.getAttribute("href")
        }) || o("type|href|height|width", function (e, t, n) {
            return n ? void 0 : e.getAttribute(t, "type" === t.toLowerCase() ? 1 : 2)
        }), w.attributes && i(function (e) {
            return e.innerHTML = "<input/>", e.firstChild.setAttribute("value", ""), "" === e.firstChild.getAttribute("value")
        }) || o("value", function (e, t, n) {
            return n || "input" !== e.nodeName.toLowerCase() ? void 0 : e.defaultValue
        }), i(function (e) {
            return null == e.getAttribute("disabled")
        }) || o(te, function (e, t, n) {
            var r;
            return n ? void 0 : e[t] === !0 ? t.toLowerCase() : (r = e.getAttributeNode(t)) && r.specified ? r.value : null
        }), t
    }(e);
    se.find = fe, se.expr = fe.selectors, se.expr[":"] = se.expr.pseudos, se.uniqueSort = se.unique = fe.uniqueSort, se.text = fe.getText, se.isXMLDoc = fe.isXML, se.contains = fe.contains;
    var pe = se.expr.match.needsContext, de = /^<([\w-]+)\s*\/?>(?:<\/\1>|)$/, he = /^.[^:#\[\.,]*$/;
    se.filter = function (e, t, n) {
        var r = t[0];
        return n && (e = ":not(" + e + ")"), 1 === t.length && 1 === r.nodeType ? se.find.matchesSelector(r, e) ? [r] : [] : se.find.matches(e, se.grep(t, function (e) {
            return 1 === e.nodeType
        }))
    }, se.fn.extend({
        find: function (e) {
            var t, n = this.length, r = [], i = this;
            if ("string" != typeof e) return this.pushStack(se(e).filter(function () {
                for (t = 0; n > t; t++) if (se.contains(i[t], this)) return !0
            }));
            for (t = 0; n > t; t++) se.find(e, i[t], r);
            return this.pushStack(n > 1 ? se.uniqueSort(r) : r)
        }, filter: function (e) {
            return this.pushStack(r(this, e || [], !1))
        }, not: function (e) {
            return this.pushStack(r(this, e || [], !0))
        }, is: function (e) {
            return !!r(this, "string" == typeof e && pe.test(e) ? se(e) : e || [], !1).length
        }
    });
    var ge, me = /^(?:\s*(<[\w\W]+>)[^>]*|#([\w-]+))$/, ve = se.fn.init = function (e, t, n) {
        var r, i;
        if (!e) return this;
        if (n = n || ge, "string" == typeof e) {
            if (r = "<" === e[0] && ">" === e[e.length - 1] && e.length >= 3 ? [null, e, null] : me.exec(e), !r || !r[1] && t) return !t || t.jquery ? (t || n).find(e) : this.constructor(t).find(e);
            if (r[1]) {
                if (t = t instanceof se ? t[0] : t, se.merge(this, se.parseHTML(r[1], t && t.nodeType ? t.ownerDocument || t : Q, !0)), de.test(r[1]) && se.isPlainObject(t)) for (r in t) se.isFunction(this[r]) ? this[r](t[r]) : this.attr(r, t[r]);
                return this
            }
            return i = Q.getElementById(r[2]), i && (this[0] = i, this.length = 1), this
        }
        return e.nodeType ? (this[0] = e, this.length = 1, this) : se.isFunction(e) ? void 0 !== n.ready ? n.ready(e) : e(se) : se.makeArray(e, this)
    };
    ve.prototype = se.fn, ge = se(Q);
    var ye = /^(?:parents|prev(?:Until|All))/, xe = {children: !0, contents: !0, next: !0, prev: !0};
    se.extend({
        dir: function (e, t, n) {
            for (var r = [], i = void 0 !== n; (e = e[t]) && 9 !== e.nodeType;) if (1 === e.nodeType) {
                if (i && se(e).is(n)) break;
                r.push(e)
            }
            return r
        }, sibling: function (e, t) {
            for (var n = []; e; e = e.nextSibling) 1 === e.nodeType && e !== t && n.push(e);
            return n
        }
    }), se.fn.extend({
        has: function (e) {
            var t = se(e, this), n = t.length;
            return this.filter(function () {
                for (var e = 0; n > e; e++) if (se.contains(this, t[e])) return !0
            })
        }, closest: function (e, t) {
            for (var n, r = 0, i = this.length, o = [], s = pe.test(e) || "string" != typeof e ? se(e, t || this.context) : 0; i > r; r++) for (n = this[r]; n && n !== t; n = n.parentNode) if (n.nodeType < 11 && (s ? s.index(n) > -1 : 1 === n.nodeType && se.find.matchesSelector(n, e))) {
                o.push(n);
                break
            }
            return this.pushStack(o.length > 1 ? se.uniqueSort(o) : o)
        }, index: function (e) {
            return e ? "string" == typeof e ? ee.call(se(e), this[0]) : ee.call(this, e.jquery ? e[0] : e) : this[0] && this[0].parentNode ? this.first().prevAll().length : -1
        }, add: function (e, t) {
            return this.pushStack(se.uniqueSort(se.merge(this.get(), se(e, t))))
        }, addBack: function (e) {
            return this.add(null == e ? this.prevObject : this.prevObject.filter(e))
        }
    }), se.each({
        parent: function (e) {
            var t = e.parentNode;
            return t && 11 !== t.nodeType ? t : null
        }, parents: function (e) {
            return se.dir(e, "parentNode")
        }, parentsUntil: function (e, t, n) {
            return se.dir(e, "parentNode", n)
        }, next: function (e) {
            return i(e, "nextSibling")
        }, prev: function (e) {
            return i(e, "previousSibling")
        }, nextAll: function (e) {
            return se.dir(e, "nextSibling")
        }, prevAll: function (e) {
            return se.dir(e, "previousSibling")
        }, nextUntil: function (e, t, n) {
            return se.dir(e, "nextSibling", n)
        }, prevUntil: function (e, t, n) {
            return se.dir(e, "previousSibling", n)
        }, siblings: function (e) {
            return se.sibling((e.parentNode || {}).firstChild, e)
        }, children: function (e) {
            return se.sibling(e.firstChild)
        }, contents: function (e) {
            return e.contentDocument || se.merge([], e.childNodes)
        }
    }, function (e, t) {
        se.fn[e] = function (n, r) {
            var i = se.map(this, t, n);
            return "Until" !== e.slice(-5) && (r = n), r && "string" == typeof r && (i = se.filter(r, i)), this.length > 1 && (xe[e] || se.uniqueSort(i), ye.test(e) && i.reverse()), this.pushStack(i)
        }
    });
    var be = /\S+/g;
    se.Callbacks = function (e) {
        e = "string" == typeof e ? o(e) : se.extend({}, e);
        var t, n, r, i, s = [], a = [], u = -1, l = function () {
            for (i = e.once, r = t = !0; a.length; u = -1) for (n = a.shift(); ++u < s.length;) s[u].apply(n[0], n[1]) === !1 && e.stopOnFalse && (u = s.length, n = !1);
            e.memory || (n = !1), t = !1, i && (s = n ? [] : "")
        }, c = {
            add: function () {
                return s && (n && !t && (u = s.length - 1, a.push(n)), function r(t) {
                    se.each(t, function (t, n) {
                        se.isFunction(n) ? e.unique && c.has(n) || s.push(n) : n && n.length && "string" !== se.type(n) && r(n)
                    })
                }(arguments), n && !t && l()), this
            }, remove: function () {
                return se.each(arguments, function (e, t) {
                    for (var n; (n = se.inArray(t, s, n)) > -1;) s.splice(n, 1), u >= n && u--
                }), this
            }, has: function (e) {
                return e ? se.inArray(e, s) > -1 : s.length > 0
            }, empty: function () {
                return s && (s = []), this
            }, disable: function () {
                return i = a = [], s = n = "", this
            }, disabled: function () {
                return !s
            }, lock: function () {
                return i = a = [], n || t || (s = n = ""), this
            }, locked: function () {
                return !!i
            }, fireWith: function (e, n) {
                return i || (n = n || [], n = [e, n.slice ? n.slice() : n], a.push(n), t || l()), this
            }, fire: function () {
                return c.fireWith(this, arguments), this
            }, fired: function () {
                return !!r
            }
        };
        return c
    }, se.extend({
        Deferred: function (t) {
            var n = [["notify", "progress", se.Callbacks("memory"), se.Callbacks("memory"), 2], ["resolve", "done", se.Callbacks("once memory"), se.Callbacks("once memory"), 0, "resolved"], ["reject", "fail", se.Callbacks("once memory"), se.Callbacks("once memory"), 1, "rejected"]],
                r = "pending", i = {
                    state: function () {
                        return r
                    }, always: function () {
                        return o.done(arguments).fail(arguments), this
                    }, "catch": function (e) {
                        return i.then(null, e)
                    }, pipe: function () {
                        var e = arguments;
                        return se.Deferred(function (t) {
                            se.each(n, function (n, r) {
                                var s = se.isFunction(e[r[4]]) && e[r[4]];
                                o[r[1]](function () {
                                    var e = s && s.apply(this, arguments);
                                    e && se.isFunction(e.promise) ? e.promise().progress(t.notify).done(t.resolve).fail(t.reject) : t[r[0] + "With"](this === i ? t.promise() : this, s ? [e] : arguments)
                                })
                            }), e = null
                        }).promise()
                    }, then: function (t, r, o) {
                        function u(t, n, r, o) {
                            return function () {
                                var c = this === i ? void 0 : this, f = arguments, p = function () {
                                    var e, i;
                                    if (!(l > t)) {
                                        if (e = r.apply(c, f), e === n.promise()) throw new TypeError("Thenable self-resolution");
                                        i = e && ("object" == typeof e || "function" == typeof e) && e.then, se.isFunction(i) ? o ? i.call(e, u(l, n, s, o), u(l, n, a, o)) : (l++, i.call(e, u(l, n, s, o), u(l, n, a, o), u(l, n, s, n.notify))) : (r !== s && (c = void 0, f = [e]), (o || n.resolveWith)(c || n.promise(), f))
                                    }
                                }, d = o ? p : function () {
                                    try {
                                        p()
                                    } catch (e) {
                                        t + 1 >= l && (r !== a && (c = void 0, f = [e]), n.rejectWith(c || n.promise(), f))
                                    }
                                };
                                t ? d() : e.setTimeout(d)
                            }
                        }

                        var l = 0;
                        return se.Deferred(function (e) {
                            n[0][3].add(u(0, e, se.isFunction(o) ? o : s, e.notifyWith)), n[1][3].add(u(0, e, se.isFunction(t) ? t : s)), n[2][3].add(u(0, e, se.isFunction(r) ? r : a))
                        }).promise()
                    }, promise: function (e) {
                        return null != e ? se.extend(e, i) : i
                    }
                }, o = {};
            return se.each(n, function (e, t) {
                var s = t[2], a = t[5];
                i[t[1]] = s.add, a && s.add(function () {
                    r = a
                }, n[3 - e][2].disable, n[0][2].lock), s.add(t[3].fire), o[t[0]] = function () {
                    return o[t[0] + "With"](this === o ? i : this, arguments), this
                }, o[t[0] + "With"] = s.fireWith
            }), i.promise(o), t && t.call(o, o), o
        }, when: function (e) {
            var t, n, r, i, o = 0, s = J.call(arguments), a = s.length,
                u = 1 !== a || e && se.isFunction(e.promise) ? a : 0, l = 1 === u ? e : se.Deferred(),
                c = function (e, t, r) {
                    return function (i) {
                        t[e] = this, r[e] = arguments.length > 1 ? J.call(arguments) : i, r === n ? l.notifyWith(t, r) : --u || l.resolveWith(t, r)
                    }
                };
            if (a > 1) for (n = new Array(a), r = new Array(a), i = new Array(a); a > o; o++) s[o] && se.isFunction(t = s[o].promise) ? t.call(s[o]).progress(c(o, r, n)).done(c(o, i, s)).fail(l.reject) : s[o] && se.isFunction(t = s[o].then) ? t.call(s[o], c(o, i, s), l.reject, c(o, r, n)) : --u;
            return u || l.resolveWith(i, s), l.promise()
        }
    });
    var we;
    se.fn.ready = function (e) {
        return se.ready.promise().done(e), this
    }, se.extend({
        isReady: !1, readyWait: 1, holdReady: function (e) {
            e ? se.readyWait++ : se.ready(!0)
        }, ready: function (e) {
            (e === !0 ? --se.readyWait : se.isReady) || (se.isReady = !0, e !== !0 && --se.readyWait > 0 || we.resolveWith(Q, [se]))
        }
    }), se.ready.promise = function (t) {
        return we || (we = se.Deferred(), "complete" === Q.readyState ? e.setTimeout(se.ready) : (Q.addEventListener("DOMContentLoaded", u), e.addEventListener("load", u))), we.promise(t)
    }, se.ready.promise();
    var Te = se.access = function (e, t, n, r, i, o, s) {
        var a = 0, u = e.length, l = null == n;
        if ("object" === se.type(n)) {
            i = !0;
            for (a in n) Te(e, t, a, n[a], !0, o, s)
        } else if (void 0 !== r && (i = !0, se.isFunction(r) || (s = !0), l && (s ? (t.call(e, r), t = null) : (l = t, t = function (e, t, n) {
            return l.call(se(e), n)
        })), t)) for (; u > a; a++) t(e[a], n, s ? r : r.call(e[a], a, t(e[a], n)));
        return i ? e : l ? t.call(e) : u ? t(e[0], n) : o
    };
    se.acceptData = function (e) {
        return 1 === e.nodeType || 9 === e.nodeType || !+e.nodeType
    }, l.uid = 1, l.accepts = se.acceptData, l.prototype = {
        register: function (e) {
            var t = {};
            return e.nodeType ? e[this.expando] = t : Object.defineProperty(e, this.expando, {
                value: t,
                writable: !0,
                configurable: !0
            }), e[this.expando]
        }, cache: function (e) {
            if (!l.accepts(e)) return {};
            var t = e[this.expando];
            return t ? t : this.register(e)
        }, set: function (e, t, n) {
            var r, i = this.cache(e);
            if ("string" == typeof t) i[se.camelCase(t)] = n; else for (r in t) i[se.camelCase(r)] = t[r];
            return i
        }, get: function (e, t) {
            var n = this.cache(e);
            return void 0 === t ? n : n[se.camelCase(t)]
        }, access: function (e, t, n) {
            return void 0 === t || t && "string" == typeof t && void 0 === n ? this.get(e, t) : (this.set(e, t, n), void 0 !== n ? n : t)
        }, remove: function (e, t) {
            var n, r = e[this.expando];
            if (void 0 !== r) {
                if (void 0 !== t) {
                    se.isArray(t) ? t = t.map(se.camelCase) : (t = se.camelCase(t), t = t in r ? [t] : t.match(be) || []), n = t.length;
                    for (; n--;) delete r[t[n]]
                }
                (void 0 === t || se.isEmptyObject(r)) && delete e[this.expando]
            }
        }, hasData: function (e) {
            var t = e[this.expando];
            return void 0 !== t && !se.isEmptyObject(t)
        }
    };
    var Ce = new l, ke = new l, Ee = /^(?:\{[\w\W]*\}|\[[\w\W]*\])$/, De = /[A-Z]/g;
    se.extend({
        hasData: function (e) {
            return ke.hasData(e) || Ce.hasData(e)
        }, data: function (e, t, n) {
            return ke.access(e, t, n)
        }, removeData: function (e, t) {
            ke.remove(e, t)
        }, _data: function (e, t, n) {
            return Ce.access(e, t, n)
        }, _removeData: function (e, t) {
            Ce.remove(e, t)
        }
    }), se.fn.extend({
        data: function (e, t) {
            var n, r, i, o = this[0], s = o && o.attributes;
            if (void 0 === e) {
                if (this.length && (i = ke.get(o), 1 === o.nodeType && !Ce.get(o, "hasDataAttrs"))) {
                    for (n = s.length; n--;) s[n] && (r = s[n].name, 0 === r.indexOf("data-") && (r = se.camelCase(r.slice(5)), c(o, r, i[r])));
                    Ce.set(o, "hasDataAttrs", !0)
                }
                return i
            }
            return "object" == typeof e ? this.each(function () {
                ke.set(this, e)
            }) : Te(this, function (t) {
                var n;
                if (o && void 0 === t) {
                    if (n = ke.get(o, e), void 0 !== n) return n;
                    if (n = c(o, e), void 0 !== n) return n
                } else this.each(function () {
                    ke.set(this, e, t)
                })
            }, null, t, arguments.length > 1, null, !0)
        }, removeData: function (e) {
            return this.each(function () {
                ke.remove(this, e)
            })
        }
    }), se.extend({
        queue: function (e, t, n) {
            var r;
            return e ? (t = (t || "fx") + "queue", r = Ce.get(e, t), n && (!r || se.isArray(n) ? r = Ce.access(e, t, se.makeArray(n)) : r.push(n)), r || []) : void 0
        }, dequeue: function (e, t) {
            t = t || "fx";
            var n = se.queue(e, t), r = n.length, i = n.shift(), o = se._queueHooks(e, t), s = function () {
                se.dequeue(e, t)
            };
            "inprogress" === i && (i = n.shift(), r--), i && ("fx" === t && n.unshift("inprogress"), delete o.stop, i.call(e, s, o)), !r && o && o.empty.fire()
        }, _queueHooks: function (e, t) {
            var n = t + "queueHooks";
            return Ce.get(e, n) || Ce.access(e, n, {
                empty: se.Callbacks("once memory").add(function () {
                    Ce.remove(e, [t + "queue", n])
                })
            })
        }
    }), se.fn.extend({
        queue: function (e, t) {
            var n = 2;
            return "string" != typeof e && (t = e, e = "fx", n--), arguments.length < n ? se.queue(this[0], e) : void 0 === t ? this : this.each(function () {
                var n = se.queue(this, e, t);
                se._queueHooks(this, e), "fx" === e && "inprogress" !== n[0] && se.dequeue(this, e)
            })
        }, dequeue: function (e) {
            return this.each(function () {
                se.dequeue(this, e)
            })
        }, clearQueue: function (e) {
            return this.queue(e || "fx", [])
        }, promise: function (e, t) {
            var n, r = 1, i = se.Deferred(), o = this, s = this.length, a = function () {
                --r || i.resolveWith(o, [o])
            };
            for ("string" != typeof e && (t = e, e = void 0), e = e || "fx"; s--;) n = Ce.get(o[s], e + "queueHooks"), n && n.empty && (r++, n.empty.add(a));
            return a(), i.promise(t)
        }
    });
    var Ne = /[+-]?(?:\d*\.|)\d+(?:[eE][+-]?\d+|)/.source, Se = new RegExp("^(?:([+-])=|)(" + Ne + ")([a-z%]*)$", "i"),
        je = ["Top", "Right", "Bottom", "Left"], Ae = function (e, t) {
            return e = t || e, "none" === se.css(e, "display") || !se.contains(e.ownerDocument, e)
        }, qe = function (e, t, n, r) {
            var i, o, s = {};
            for (o in t) s[o] = e.style[o], e.style[o] = t[o];
            i = n.apply(e, r || []);
            for (o in t) e.style[o] = s[o];
            return i
        }, Le = /^(?:checkbox|radio)$/i, He = /<([\w:-]+)/, Fe = /^$|\/(?:java|ecma)script/i, Oe = {
            option: [1, "<select multiple='multiple'>", "</select>"],
            thead: [1, "<table>", "</table>"],
            col: [2, "<table><colgroup>", "</colgroup></table>"],
            tr: [2, "<table>", "</table>"],
            td: [3, "<table>", "</table>"],
            _default: [0, "", ""]
        };
    Oe.optgroup = Oe.option, Oe.tbody = Oe.tfoot = Oe.colgroup = Oe.caption = Oe.thead, Oe.th = Oe.td;
    var Re = /<|&#?\w+;/;
    !function () {
        var e = Q.createDocumentFragment(), t = e.appendChild(Q.createElement("div")), n = Q.createElement("input");
        n.setAttribute("type", "radio"), n.setAttribute("checked", "checked"), n.setAttribute("name", "t"), t.appendChild(n), ie.checkClone = t.cloneNode(!0).cloneNode(!0).lastChild.checked, t.innerHTML = "<textarea>x</textarea>", ie.noCloneChecked = !!t.cloneNode(!0).lastChild.defaultValue
    }(), ie.focusin = "onfocusin" in e;
    var Pe = /^key/, Me = /^(?:mouse|pointer|contextmenu|drag|drop)|click/, We = /^(?:focusinfocus|focusoutblur)$/,
        Ie = /^([^.]*)(?:\.(.+)|)/;
    se.event = {
        global: {},
        add: function (e, t, n, r, i) {
            var o, s, a, u, l, c, f, p, d, h, g, m = Ce.get(e);
            if (m) for (n.handler && (o = n, n = o.handler, i = o.selector), n.guid || (n.guid = se.guid++), (u = m.events) || (u = m.events = {}), (s = m.handle) || (s = m.handle = function (t) {
                return "undefined" != typeof se && se.event.triggered !== t.type ? se.event.dispatch.apply(e, arguments) : void 0
            }), t = (t || "").match(be) || [""], l = t.length; l--;) a = Ie.exec(t[l]) || [], d = g = a[1], h = (a[2] || "").split(".").sort(), d && (f = se.event.special[d] || {}, d = (i ? f.delegateType : f.bindType) || d, f = se.event.special[d] || {}, c = se.extend({
                type: d,
                origType: g,
                data: r,
                handler: n,
                guid: n.guid,
                selector: i,
                needsContext: i && se.expr.match.needsContext.test(i),
                namespace: h.join(".")
            }, o), (p = u[d]) || (p = u[d] = [], p.delegateCount = 0, f.setup && f.setup.call(e, r, h, s) !== !1 || e.addEventListener && e.addEventListener(d, s)), f.add && (f.add.call(e, c), c.handler.guid || (c.handler.guid = n.guid)), i ? p.splice(p.delegateCount++, 0, c) : p.push(c), se.event.global[d] = !0)
        },
        remove: function (e, t, n, r, i) {
            var o, s, a, u, l, c, f, p, d, h, g, m = Ce.hasData(e) && Ce.get(e);
            if (m && (u = m.events)) {
                for (t = (t || "").match(be) || [""], l = t.length; l--;) if (a = Ie.exec(t[l]) || [], d = g = a[1], h = (a[2] || "").split(".").sort(), d) {
                    for (f = se.event.special[d] || {}, d = (r ? f.delegateType : f.bindType) || d, p = u[d] || [], a = a[2] && new RegExp("(^|\\.)" + h.join("\\.(?:.*\\.|)") + "(\\.|$)"), s = o = p.length; o--;) c = p[o], !i && g !== c.origType || n && n.guid !== c.guid || a && !a.test(c.namespace) || r && r !== c.selector && ("**" !== r || !c.selector) || (p.splice(o, 1), c.selector && p.delegateCount--, f.remove && f.remove.call(e, c));
                    s && !p.length && (f.teardown && f.teardown.call(e, h, m.handle) !== !1 || se.removeEvent(e, d, m.handle), delete u[d])
                } else for (d in u) se.event.remove(e, d + t[l], n, r, !0);
                se.isEmptyObject(u) && Ce.remove(e, "handle events")
            }
        },
        trigger: function (t, n, r, i) {
            var o, s, a, u, l, c, f, p = [r || Q], d = re.call(t, "type") ? t.type : t,
                h = re.call(t, "namespace") ? t.namespace.split(".") : [];
            if (s = a = r = r || Q, 3 !== r.nodeType && 8 !== r.nodeType && !We.test(d + se.event.triggered) && (d.indexOf(".") > -1 && (h = d.split("."), d = h.shift(), h.sort()), l = d.indexOf(":") < 0 && "on" + d, t = t[se.expando] ? t : new se.Event(d, "object" == typeof t && t), t.isTrigger = i ? 2 : 3, t.namespace = h.join("."), t.rnamespace = t.namespace ? new RegExp("(^|\\.)" + h.join("\\.(?:.*\\.|)") + "(\\.|$)") : null, t.result = void 0, t.target || (t.target = r), n = null == n ? [t] : se.makeArray(n, [t]), f = se.event.special[d] || {}, i || !f.trigger || f.trigger.apply(r, n) !== !1)) {
                if (!i && !f.noBubble && !se.isWindow(r)) {
                    for (u = f.delegateType || d, We.test(u + d) || (s = s.parentNode); s; s = s.parentNode) p.push(s), a = s;
                    a === (r.ownerDocument || Q) && p.push(a.defaultView || a.parentWindow || e)
                }
                for (o = 0; (s = p[o++]) && !t.isPropagationStopped();) t.type = o > 1 ? u : f.bindType || d, c = (Ce.get(s, "events") || {})[t.type] && Ce.get(s, "handle"), c && c.apply(s, n), c = l && s[l], c && c.apply && se.acceptData(s) && (t.result = c.apply(s, n), t.result === !1 && t.preventDefault());
                return t.type = d, i || t.isDefaultPrevented() || f._default && f._default.apply(p.pop(), n) !== !1 || !se.acceptData(r) || l && se.isFunction(r[d]) && !se.isWindow(r) && (a = r[l], a && (r[l] = null), se.event.triggered = d, r[d](), se.event.triggered = void 0, a && (r[l] = a)), t.result
            }
        },
        dispatch: function (e) {
            e = se.event.fix(e);
            var t, n, r, i, o, s = [], a = J.call(arguments), u = (Ce.get(this, "events") || {})[e.type] || [],
                l = se.event.special[e.type] || {};
            if (a[0] = e, e.delegateTarget = this, !l.preDispatch || l.preDispatch.call(this, e) !== !1) {
                for (s = se.event.handlers.call(this, e, u), t = 0; (i = s[t++]) && !e.isPropagationStopped();) for (e.currentTarget = i.elem, n = 0; (o = i.handlers[n++]) && !e.isImmediatePropagationStopped();) e.rnamespace && !e.rnamespace.test(o.namespace) || (e.handleObj = o, e.data = o.data, r = ((se.event.special[o.origType] || {}).handle || o.handler).apply(i.elem, a), void 0 !== r && (e.result = r) === !1 && (e.preventDefault(), e.stopPropagation()));
                return l.postDispatch && l.postDispatch.call(this, e), e.result
            }
        },
        handlers: function (e, t) {
            var n, r, i, o, s = [], a = t.delegateCount, u = e.target;
            if (a && u.nodeType && (!e.button || "click" !== e.type)) for (; u !== this; u = u.parentNode || this) if (u.disabled !== !0 || "click" !== e.type) {
                for (r = [], n = 0; a > n; n++) o = t[n], i = o.selector + " ", void 0 === r[i] && (r[i] = o.needsContext ? se(i, this).index(u) > -1 : se.find(i, this, null, [u]).length), r[i] && r.push(o);
                r.length && s.push({elem: u, handlers: r})
            }
            return a < t.length && s.push({elem: this, handlers: t.slice(a)}), s
        },
        props: "altKey bubbles cancelable ctrlKey currentTarget detail eventPhase metaKey relatedTarget shiftKey target timeStamp view which".split(" "),
        fixHooks: {},
        keyHooks: {
            props: "char charCode key keyCode".split(" "), filter: function (e, t) {
                return null == e.which && (e.which = null != t.charCode ? t.charCode : t.keyCode), e
            }
        },
        mouseHooks: {
            props: "button buttons clientX clientY offsetX offsetY pageX pageY screenX screenY toElement".split(" "),
            filter: function (e, t) {
                var n, r, i, o = t.button;
                return null == e.pageX && null != t.clientX && (n = e.target.ownerDocument || Q, r = n.documentElement, i = n.body, e.pageX = t.clientX + (r && r.scrollLeft || i && i.scrollLeft || 0) - (r && r.clientLeft || i && i.clientLeft || 0), e.pageY = t.clientY + (r && r.scrollTop || i && i.scrollTop || 0) - (r && r.clientTop || i && i.clientTop || 0)), e.which || void 0 === o || (e.which = 1 & o ? 1 : 2 & o ? 3 : 4 & o ? 2 : 0), e
            }
        },
        fix: function (e) {
            if (e[se.expando]) return e;
            var t, n, r, i = e.type, o = e, s = this.fixHooks[i];
            for (s || (this.fixHooks[i] = s = Me.test(i) ? this.mouseHooks : Pe.test(i) ? this.keyHooks : {}), r = s.props ? this.props.concat(s.props) : this.props, e = new se.Event(o), t = r.length; t--;) n = r[t], e[n] = o[n];
            return 3 === e.target.nodeType && (e.target = e.target.parentNode), s.filter ? s.filter(e, o) : e
        },
        special: {
            load: {noBubble: !0}, focus: {
                trigger: function () {
                    return this !== y() && this.focus ? (this.focus(), !1) : void 0
                }, delegateType: "focusin"
            }, blur: {
                trigger: function () {
                    return this === y() && this.blur ? (this.blur(), !1) : void 0
                }, delegateType: "focusout"
            }, click: {
                trigger: function () {
                    return "checkbox" === this.type && this.click && se.nodeName(this, "input") ? (this.click(), !1) : void 0
                }, _default: function (e) {
                    return se.nodeName(e.target, "a")
                }
            }, beforeunload: {
                postDispatch: function (e) {
                    void 0 !== e.result && e.originalEvent && (e.originalEvent.returnValue = e.result)
                }
            }
        },
        simulate: function (e, t, n) {
            var r = se.extend(new se.Event, n, {type: e, isSimulated: !0});
            se.event.trigger(r, null, t), r.isDefaultPrevented() && n.preventDefault()
        }
    }, se.removeEvent = function (e, t, n) {
        e.removeEventListener && e.removeEventListener(t, n)
    }, se.Event = function (e, t) {
        return this instanceof se.Event ? (e && e.type ? (this.originalEvent = e, this.type = e.type, this.isDefaultPrevented = e.defaultPrevented || void 0 === e.defaultPrevented && e.returnValue === !1 ? m : v) : this.type = e, t && se.extend(this, t), this.timeStamp = e && e.timeStamp || se.now(), void(this[se.expando] = !0)) : new se.Event(e, t)
    }, se.Event.prototype = {
        constructor: se.Event,
        isDefaultPrevented: v,
        isPropagationStopped: v,
        isImmediatePropagationStopped: v,
        preventDefault: function () {
            var e = this.originalEvent;
            this.isDefaultPrevented = m, e && e.preventDefault()
        },
        stopPropagation: function () {
            var e = this.originalEvent;
            this.isPropagationStopped = m, e && e.stopPropagation()
        },
        stopImmediatePropagation: function () {
            var e = this.originalEvent;
            this.isImmediatePropagationStopped = m, e && e.stopImmediatePropagation(), this.stopPropagation()
        }
    }, se.each({
        mouseenter: "mouseover",
        mouseleave: "mouseout",
        pointerenter: "pointerover",
        pointerleave: "pointerout"
    }, function (e, t) {
        se.event.special[e] = {
            delegateType: t, bindType: t, handle: function (e) {
                var n, r = this, i = e.relatedTarget, o = e.handleObj;
                return i && (i === r || se.contains(r, i)) || (e.type = o.origType, n = o.handler.apply(this, arguments), e.type = t), n
            }
        }
    }), ie.focusin || se.each({focus: "focusin", blur: "focusout"}, function (e, t) {
        var n = function (e) {
            se.event.simulate(t, e.target, se.event.fix(e))
        };
        se.event.special[t] = {
            setup: function () {
                var r = this.ownerDocument || this, i = Ce.access(r, t);
                i || r.addEventListener(e, n, !0), Ce.access(r, t, (i || 0) + 1)
            }, teardown: function () {
                var r = this.ownerDocument || this, i = Ce.access(r, t) - 1;
                i ? Ce.access(r, t, i) : (r.removeEventListener(e, n, !0), Ce.remove(r, t))
            }
        }
    }), se.fn.extend({
        on: function (e, t, n, r) {
            return x(this, e, t, n, r)
        }, one: function (e, t, n, r) {
            return x(this, e, t, n, r, 1)
        }, off: function (e, t, n) {
            var r, i;
            if (e && e.preventDefault && e.handleObj) return r = e.handleObj, se(e.delegateTarget).off(r.namespace ? r.origType + "." + r.namespace : r.origType, r.selector, r.handler), this;
            if ("object" == typeof e) {
                for (i in e) this.off(i, t, e[i]);
                return this
            }
            return t !== !1 && "function" != typeof t || (n = t, t = void 0), n === !1 && (n = v), this.each(function () {
                se.event.remove(this, e, n, t)
            })
        }, trigger: function (e, t) {
            return this.each(function () {
                se.event.trigger(e, t, this)
            })
        }, triggerHandler: function (e, t) {
            var n = this[0];
            return n ? se.event.trigger(e, t, n, !0) : void 0
        }
    });
    var $e = /<(?!area|br|col|embed|hr|img|input|link|meta|param)(([\w:-]+)[^>]*)\/>/gi, Be = /<(?:script|style|link)/i,
        _e = /checked\s*(?:[^=]|=\s*.checked.)/i, Xe = /^true\/(.*)/, ze = /^\s*<!(?:\[CDATA\[|--)|(?:\]\]|--)>\s*$/g;
    se.extend({
        htmlPrefilter: function (e) {
            return e.replace($e, "<$1></$2>")
        }, clone: function (e, t, n) {
            var r, i, o, s, a = e.cloneNode(!0), u = se.contains(e.ownerDocument, e);
            if (!(ie.noCloneChecked || 1 !== e.nodeType && 11 !== e.nodeType || se.isXMLDoc(e))) for (s = d(a), o = d(e), r = 0, i = o.length; i > r; r++) k(o[r], s[r]);
            if (t) if (n) for (o = o || d(e), s = s || d(a), r = 0, i = o.length; i > r; r++) C(o[r], s[r]); else C(e, a);
            return s = d(a, "script"), s.length > 0 && h(s, !u && d(e, "script")), a
        }, cleanData: function (e) {
            for (var t, n, r, i = se.event.special, o = 0; void 0 !== (n = e[o]); o++) if (se.acceptData(n) && (t = n[Ce.expando])) {
                if (t.events) for (r in t.events) i[r] ? se.event.remove(n, r) : se.removeEvent(n, r, t.handle);
                delete n[Ce.expando]
            }
        }
    }), se.fn.extend({
        detach: function (e) {
            return D(this, e, !0)
        }, remove: function (e) {
            return D(this, e)
        }, text: function (e) {
            return Te(this, function (e) {
                return void 0 === e ? se.text(this) : this.empty().each(function () {
                    1 !== this.nodeType && 11 !== this.nodeType && 9 !== this.nodeType || (this.textContent = e)
                })
            }, null, e, arguments.length)
        }, append: function () {
            return E(this, arguments, function (e) {
                if (1 === this.nodeType || 11 === this.nodeType || 9 === this.nodeType) {
                    var t = b(this, e);
                    t.appendChild(e)
                }
            })
        }, prepend: function () {
            return E(this, arguments, function (e) {
                if (1 === this.nodeType || 11 === this.nodeType || 9 === this.nodeType) {
                    var t = b(this, e);
                    t.insertBefore(e, t.firstChild)
                }
            })
        }, before: function () {
            return E(this, arguments, function (e) {
                this.parentNode && this.parentNode.insertBefore(e, this)
            })
        }, after: function () {
            return E(this, arguments, function (e) {
                this.parentNode && this.parentNode.insertBefore(e, this.nextSibling)
            })
        }, empty: function () {
            for (var e, t = 0; null != (e = this[t]); t++) 1 === e.nodeType && (se.cleanData(d(e, !1)), e.textContent = "");
            return this
        }, clone: function (e, t) {
            return e = null == e ? !1 : e, t = null == t ? e : t, this.map(function () {
                return se.clone(this, e, t)
            })
        }, html: function (e) {
            return Te(this, function (e) {
                var t = this[0] || {}, n = 0, r = this.length;
                if (void 0 === e && 1 === t.nodeType) return t.innerHTML;
                if ("string" == typeof e && !Be.test(e) && !Oe[(He.exec(e) || ["", ""])[1].toLowerCase()]) {
                    e = se.htmlPrefilter(e);
                    try {
                        for (; r > n; n++) t = this[n] || {}, 1 === t.nodeType && (se.cleanData(d(t, !1)), t.innerHTML = e);
                        t = 0
                    } catch (i) {
                    }
                }
                t && this.empty().append(e)
            }, null, e, arguments.length)
        }, replaceWith: function () {
            var e = [];
            return E(this, arguments, function (t) {
                var n = this.parentNode;
                se.inArray(this, e) < 0 && (se.cleanData(d(this)), n && n.replaceChild(t, this))
            }, e)
        }
    }), se.each({
        appendTo: "append",
        prependTo: "prepend",
        insertBefore: "before",
        insertAfter: "after",
        replaceAll: "replaceWith"
    }, function (e, t) {
        se.fn[e] = function (e) {
            for (var n, r = [], i = se(e), o = i.length - 1, s = 0; o >= s; s++) n = s === o ? this : this.clone(!0), se(i[s])[t](n), Z.apply(r, n.get());
            return this.pushStack(r)
        }
    });
    var Ue = /^margin/, Ve = new RegExp("^(" + Ne + ")(?!px)[a-z%]+$", "i"), Ye = function (t) {
        var n = t.ownerDocument.defaultView;
        return n.opener || (n = e), n.getComputedStyle(t)
    }, Ge = Q.documentElement;
    !function () {
        function t() {
            s.style.cssText = "-webkit-box-sizing:border-box;box-sizing:border-box;display:block;position:absolute;margin:0;margin-top:1%;margin-right:50%;border:1px;padding:1px;top:1%;width:50%;height:4px", s.innerHTML = "", Ge.appendChild(o);
            var t = e.getComputedStyle(s);
            n = "1%" !== t.top, r = "4px" === t.height, i = "4px" === t.marginRight, Ge.removeChild(o)
        }

        var n, r, i, o = Q.createElement("div"), s = Q.createElement("div");
        s.style && (s.style.backgroundClip = "content-box", s.cloneNode(!0).style.backgroundClip = "", ie.clearCloneStyle = "content-box" === s.style.backgroundClip, o.style.cssText = "border:0;width:8px;height:0;top:0;left:-9999px;padding:0;margin-top:1px;position:absolute", o.appendChild(s), se.extend(ie, {
            pixelPosition: function () {
                return t(), n
            }, boxSizingReliable: function () {
                return null == r && t(), r
            }, pixelMarginRight: function () {
                return null == r && t(), i
            }, reliableMarginRight: function () {
                var t, n = s.appendChild(Q.createElement("div"));
                return n.style.cssText = s.style.cssText = "-webkit-box-sizing:content-box;box-sizing:content-box;display:block;margin:0;border:0;padding:0", n.style.marginRight = n.style.width = "0", s.style.width = "1px", Ge.appendChild(o), t = !parseFloat(e.getComputedStyle(n).marginRight), Ge.removeChild(o), s.removeChild(n), t
            }
        }))
    }();
    var Qe = /^(none|table(?!-c[ea]).+)/, Je = new RegExp("^(" + Ne + ")(.*)$", "i"),
        Ke = {position: "absolute", visibility: "hidden", display: "block"},
        Ze = {letterSpacing: "0", fontWeight: "400"}, et = ["Webkit", "Moz", "ms"], tt = Q.createElement("div").style;
    se.extend({
        cssHooks: {
            opacity: {
                get: function (e, t) {
                    if (t) {
                        var n = N(e, "opacity");
                        return "" === n ? "1" : n
                    }
                }
            }
        },
        cssNumber: {
            columnCount: !0,
            fillOpacity: !0,
            flexGrow: !0,
            flexShrink: !0,
            fontWeight: !0,
            lineHeight: !0,
            opacity: !0,
            order: !0,
            orphans: !0,
            widows: !0,
            zIndex: !0,
            zoom: !0
        },
        cssProps: {"float": "cssFloat"},
        style: function (e, t, n, r) {
            if (e && 3 !== e.nodeType && 8 !== e.nodeType && e.style) {
                var i, o, s, a = se.camelCase(t), u = e.style;
                return t = se.cssProps[a] || (se.cssProps[a] = j(a) || a), s = se.cssHooks[t] || se.cssHooks[a], void 0 === n ? s && "get" in s && void 0 !== (i = s.get(e, !1, r)) ? i : u[t] : (o = typeof n, "string" === o && (i = Se.exec(n)) && i[1] && (n = f(e, t, i), o = "number"), null != n && n === n && ("number" === o && (n += i && i[3] || (se.cssNumber[a] ? "" : "px")), ie.clearCloneStyle || "" !== n || 0 !== t.indexOf("background") || (u[t] = "inherit"), s && "set" in s && void 0 === (n = s.set(e, n, r)) || (u[t] = n)), void 0)
            }
        },
        css: function (e, t, n, r) {
            var i, o, s, a = se.camelCase(t);
            return t = se.cssProps[a] || (se.cssProps[a] = j(a) || a), s = se.cssHooks[t] || se.cssHooks[a], s && "get" in s && (i = s.get(e, !0, n)), void 0 === i && (i = N(e, t, r)), "normal" === i && t in Ze && (i = Ze[t]), "" === n || n ? (o = parseFloat(i), n === !0 || se.isNumeric(o) ? o || 0 : i) : i
        }
    }), se.each(["height", "width"], function (e, t) {
        se.cssHooks[t] = {
            get: function (e, n, r) {
                return n ? !Qe.test(se.css(e, "display")) || e.getClientRects().length && e.getBoundingClientRect().width ? L(e, t, r) : qe(e, Ke, function () {
                    return L(e, t, r)
                }) : void 0
            }, set: function (e, n, r) {
                var i = r && Ye(e);
                return A(e, n, r ? q(e, t, r, "border-box" === se.css(e, "boxSizing", !1, i), i) : 0)
            }
        }
    }), se.cssHooks.marginRight = S(ie.reliableMarginRight, function (e, t) {
        return t ? qe(e, {display: "inline-block"}, N, [e, "marginRight"]) : void 0
    }), se.each({margin: "", padding: "", border: "Width"}, function (e, t) {
        se.cssHooks[e + t] = {
            expand: function (n) {
                for (var r = 0, i = {}, o = "string" == typeof n ? n.split(" ") : [n]; 4 > r; r++) i[e + je[r] + t] = o[r] || o[r - 2] || o[0];
                return i
            }
        }, Ue.test(e) || (se.cssHooks[e + t].set = A)
    }), se.fn.extend({
        css: function (e, t) {
            return Te(this, function (e, t, n) {
                var r, i, o = {}, s = 0;
                if (se.isArray(t)) {
                    for (r = Ye(e), i = t.length; i > s; s++) o[t[s]] = se.css(e, t[s], !1, r);
                    return o
                }
                return void 0 !== n ? se.style(e, t, n) : se.css(e, t)
            }, e, t, arguments.length > 1)
        }, show: function () {
            return p(this, !0)
        }, hide: function () {
            return p(this)
        }, toggle: function (e) {
            return "boolean" == typeof e ? e ? this.show() : this.hide() : this.each(function () {
                Ae(this) ? se(this).show() : se(this).hide()
            })
        }
    }), se.Tween = H, H.prototype = {
        constructor: H, init: function (e, t, n, r, i, o) {
            this.elem = e, this.prop = n, this.easing = i || se.easing._default, this.options = t, this.start = this.now = this.cur(), this.end = r, this.unit = o || (se.cssNumber[n] ? "" : "px")
        }, cur: function () {
            var e = H.propHooks[this.prop];
            return e && e.get ? e.get(this) : H.propHooks._default.get(this)
        }, run: function (e) {
            var t, n = H.propHooks[this.prop];
            return this.options.duration ? this.pos = t = se.easing[this.easing](e, this.options.duration * e, 0, 1, this.options.duration) : this.pos = t = e, this.now = (this.end - this.start) * t + this.start, this.options.step && this.options.step.call(this.elem, this.now, this), n && n.set ? n.set(this) : H.propHooks._default.set(this), this
        }
    }, H.prototype.init.prototype = H.prototype, H.propHooks = {
        _default: {
            get: function (e) {
                var t;
                return 1 !== e.elem.nodeType || null != e.elem[e.prop] && null == e.elem.style[e.prop] ? e.elem[e.prop] : (t = se.css(e.elem, e.prop, ""), t && "auto" !== t ? t : 0)
            }, set: function (e) {
                se.fx.step[e.prop] ? se.fx.step[e.prop](e) : 1 !== e.elem.nodeType || null == e.elem.style[se.cssProps[e.prop]] && !se.cssHooks[e.prop] ? e.elem[e.prop] = e.now : se.style(e.elem, e.prop, e.now + e.unit)
            }
        }
    }, H.propHooks.scrollTop = H.propHooks.scrollLeft = {
        set: function (e) {
            e.elem.nodeType && e.elem.parentNode && (e.elem[e.prop] = e.now)
        }
    }, se.easing = {
        linear: function (e) {
            return e
        }, swing: function (e) {
            return .5 - Math.cos(e * Math.PI) / 2
        }, _default: "swing"
    }, se.fx = H.prototype.init, se.fx.step = {};
    var nt, rt, it = /^(?:toggle|show|hide)$/, ot = /queueHooks$/;
    se.Animation = se.extend(I, {
        tweeners: {
            "*": [function (e, t) {
                var n = this.createTween(e, t);
                return f(n.elem, e, Se.exec(t), n), n
            }]
        }, tweener: function (e, t) {
            se.isFunction(e) ? (t = e, e = ["*"]) : e = e.match(be);
            for (var n, r = 0, i = e.length; i > r; r++) n = e[r], I.tweeners[n] = I.tweeners[n] || [], I.tweeners[n].unshift(t)
        }, prefilters: [M], prefilter: function (e, t) {
            t ? I.prefilters.unshift(e) : I.prefilters.push(e)
        }
    }), se.speed = function (e, t, n) {
        var r = e && "object" == typeof e ? se.extend({}, e) : {
            complete: n || !n && t || se.isFunction(e) && e,
            duration: e,
            easing: n && t || t && !se.isFunction(t) && t
        };
        return se.fx.off || Q.hidden ? r.duration = 0 : r.duration = "number" == typeof r.duration ? r.duration : r.duration in se.fx.speeds ? se.fx.speeds[r.duration] : se.fx.speeds._default, null != r.queue && r.queue !== !0 || (r.queue = "fx"), r.old = r.complete, r.complete = function () {
            se.isFunction(r.old) && r.old.call(this), r.queue && se.dequeue(this, r.queue)
        }, r
    }, se.fn.extend({
        fadeTo: function (e, t, n, r) {
            return this.filter(Ae).css("opacity", 0).show().end().animate({opacity: t}, e, n, r)
        }, animate: function (e, t, n, r) {
            var i = se.isEmptyObject(e), o = se.speed(t, n, r), s = function () {
                var t = I(this, se.extend({}, e), o);
                (i || Ce.get(this, "finish")) && t.stop(!0)
            };
            return s.finish = s, i || o.queue === !1 ? this.each(s) : this.queue(o.queue, s)
        }, stop: function (e, t, n) {
            var r = function (e) {
                var t = e.stop;
                delete e.stop, t(n)
            };
            return "string" != typeof e && (n = t, t = e, e = void 0), t && e !== !1 && this.queue(e || "fx", []), this.each(function () {
                var t = !0, i = null != e && e + "queueHooks", o = se.timers, s = Ce.get(this);
                if (i) s[i] && s[i].stop && r(s[i]); else for (i in s) s[i] && s[i].stop && ot.test(i) && r(s[i]);
                for (i = o.length; i--;) o[i].elem !== this || null != e && o[i].queue !== e || (o[i].anim.stop(n), t = !1, o.splice(i, 1));
                !t && n || se.dequeue(this, e)
            })
        }, finish: function (e) {
            return e !== !1 && (e = e || "fx"), this.each(function () {
                var t, n = Ce.get(this), r = n[e + "queue"], i = n[e + "queueHooks"], o = se.timers,
                    s = r ? r.length : 0;
                for (n.finish = !0, se.queue(this, e, []), i && i.stop && i.stop.call(this, !0), t = o.length; t--;) o[t].elem === this && o[t].queue === e && (o[t].anim.stop(!0), o.splice(t, 1));
                for (t = 0; s > t; t++) r[t] && r[t].finish && r[t].finish.call(this);
                delete n.finish
            })
        }
    }), se.each(["toggle", "show", "hide"], function (e, t) {
        var n = se.fn[t];
        se.fn[t] = function (e, r, i) {
            return null == e || "boolean" == typeof e ? n.apply(this, arguments) : this.animate(R(t, !0), e, r, i)
        }
    }), se.each({
        slideDown: R("show"),
        slideUp: R("hide"),
        slideToggle: R("toggle"),
        fadeIn: {opacity: "show"},
        fadeOut: {opacity: "hide"},
        fadeToggle: {opacity: "toggle"}
    }, function (e, t) {
        se.fn[e] = function (e, n, r) {
            return this.animate(t, e, n, r)
        }
    }), se.timers = [], se.fx.tick = function () {
        var e, t = 0, n = se.timers;
        for (nt = se.now(); t < n.length; t++) e = n[t], e() || n[t] !== e || n.splice(t--, 1);
        n.length || se.fx.stop(), nt = void 0
    }, se.fx.timer = function (e) {
        se.timers.push(e), e() ? se.fx.start() : se.timers.pop()
    }, se.fx.interval = 13, se.fx.start = function () {
        rt || (rt = e.requestAnimationFrame ? e.requestAnimationFrame(F) : e.setInterval(se.fx.tick, se.fx.interval))
    }, se.fx.stop = function () {
        e.cancelAnimationFrame ? e.cancelAnimationFrame(rt) : e.clearInterval(rt), rt = null
    }, se.fx.speeds = {slow: 600, fast: 200, _default: 400}, se.fn.delay = function (t, n) {
        return t = se.fx ? se.fx.speeds[t] || t : t, n = n || "fx", this.queue(n, function (n, r) {
            var i = e.setTimeout(n, t);
            r.stop = function () {
                e.clearTimeout(i)
            }
        })
    }, function () {
        var e = Q.createElement("input"), t = Q.createElement("select"), n = t.appendChild(Q.createElement("option"));
        e.type = "checkbox", ie.checkOn = "" !== e.value, ie.optSelected = n.selected, t.disabled = !0, ie.optDisabled = !n.disabled, e = Q.createElement("input"), e.value = "t", e.type = "radio", ie.radioValue = "t" === e.value
    }();
    var st, at = se.expr.attrHandle;
    se.fn.extend({
        attr: function (e, t) {
            return Te(this, se.attr, e, t, arguments.length > 1)
        }, removeAttr: function (e) {
            return this.each(function () {
                se.removeAttr(this, e)
            })
        }
    }), se.extend({
        attr: function (e, t, n) {
            var r, i, o = e.nodeType;
            if (3 !== o && 8 !== o && 2 !== o) return "undefined" == typeof e.getAttribute ? se.prop(e, t, n) : (1 === o && se.isXMLDoc(e) || (t = t.toLowerCase(), i = se.attrHooks[t] || (se.expr.match.bool.test(t) ? st : void 0)), void 0 !== n ? null === n ? void se.removeAttr(e, t) : i && "set" in i && void 0 !== (r = i.set(e, n, t)) ? r : (e.setAttribute(t, n + ""), n) : i && "get" in i && null !== (r = i.get(e, t)) ? r : (r = se.find.attr(e, t), null == r ? void 0 : r))
        }, attrHooks: {
            type: {
                set: function (e, t) {
                    if (!ie.radioValue && "radio" === t && se.nodeName(e, "input")) {
                        var n = e.value;
                        return e.setAttribute("type", t), n && (e.value = n), t
                    }
                }
            }
        }, removeAttr: function (e, t) {
            var n, r, i = 0, o = t && t.match(be);
            if (o && 1 === e.nodeType) for (; n = o[i++];) r = se.propFix[n] || n, se.expr.match.bool.test(n) && (e[r] = !1), e.removeAttribute(n)
        }
    }), st = {
        set: function (e, t, n) {
            return t === !1 ? se.removeAttr(e, n) : e.setAttribute(n, n), n
        }
    }, se.each(se.expr.match.bool.source.match(/\w+/g), function (e, t) {
        var n = at[t] || se.find.attr;
        at[t] = function (e, t, r) {
            var i, o;
            return r || (o = at[t], at[t] = i, i = null != n(e, t, r) ? t.toLowerCase() : null, at[t] = o), i
        }
    });
    var ut = /^(?:input|select|textarea|button)$/i;
    se.fn.extend({
        prop: function (e, t) {
            return Te(this, se.prop, e, t, arguments.length > 1)
        }, removeProp: function (e) {
            return this.each(function () {
                delete this[se.propFix[e] || e]
            })
        }
    }), se.extend({
        prop: function (e, t, n) {
            var r, i, o = e.nodeType;
            if (3 !== o && 8 !== o && 2 !== o) return 1 === o && se.isXMLDoc(e) || (t = se.propFix[t] || t, i = se.propHooks[t]), void 0 !== n ? i && "set" in i && void 0 !== (r = i.set(e, n, t)) ? r : e[t] = n : i && "get" in i && null !== (r = i.get(e, t)) ? r : e[t]
        }, propHooks: {
            tabIndex: {
                get: function (e) {
                    return e.hasAttribute("tabindex") || ut.test(e.nodeName) || e.href ? e.tabIndex : -1
                }
            }
        }, propFix: {"for": "htmlFor", "class": "className"}
    }), ie.optSelected || (se.propHooks.selected = {
        get: function (e) {
            var t = e.parentNode;
            return t && t.parentNode && t.parentNode.selectedIndex, null
        }
    }), se.each(["tabIndex", "readOnly", "maxLength", "cellSpacing", "cellPadding", "rowSpan", "colSpan", "useMap", "frameBorder", "contentEditable"], function () {
        se.propFix[this.toLowerCase()] = this
    });
    var lt = /[\t\r\n\f]/g;
    se.fn.extend({
        addClass: function (e) {
            var t, n, r, i, o, s, a, u = "string" == typeof e && e, l = 0, c = this.length;
            if (se.isFunction(e)) return this.each(function (t) {
                se(this).addClass(e.call(this, t, $(this)))
            });
            if (u) for (t = (e || "").match(be) || []; c > l; l++) if (n = this[l], i = $(n), r = 1 === n.nodeType && (" " + i + " ").replace(lt, " ")) {
                for (s = 0; o = t[s++];) r.indexOf(" " + o + " ") < 0 && (r += o + " ");
                a = se.trim(r), i !== a && n.setAttribute("class", a)
            }
            return this
        }, removeClass: function (e) {
            var t, n, r, i, o, s, a, u = 0 === arguments.length || "string" == typeof e && e, l = 0, c = this.length;
            if (se.isFunction(e)) return this.each(function (t) {
                se(this).removeClass(e.call(this, t, $(this)))
            });
            if (u) for (t = (e || "").match(be) || []; c > l; l++) if (n = this[l], i = $(n), r = 1 === n.nodeType && (" " + i + " ").replace(lt, " ")) {
                for (s = 0; o = t[s++];) for (; r.indexOf(" " + o + " ") > -1;) r = r.replace(" " + o + " ", " ");
                a = e ? se.trim(r) : "", i !== a && n.setAttribute("class", a)
            }
            return this
        }, toggleClass: function (e, t) {
            var n = typeof e;
            return "boolean" == typeof t && "string" === n ? t ? this.addClass(e) : this.removeClass(e) : se.isFunction(e) ? this.each(function (n) {
                se(this).toggleClass(e.call(this, n, $(this), t), t)
            }) : this.each(function () {
                var t, r, i, o;
                if ("string" === n) for (r = 0, i = se(this), o = e.match(be) || []; t = o[r++];) i.hasClass(t) ? i.removeClass(t) : i.addClass(t); else void 0 !== e && "boolean" !== n || (t = $(this), t && Ce.set(this, "__className__", t), this.setAttribute && this.setAttribute("class", t || e === !1 ? "" : Ce.get(this, "__className__") || ""))
            })
        }, hasClass: function (e) {
            for (var t = " " + e + " ", n = 0, r = this.length; r > n; n++) if (1 === this[n].nodeType && (" " + $(this[n]) + " ").replace(lt, " ").indexOf(t) > -1) return !0;
            return !1
        }
    });
    var ct = /\r/g;
    se.fn.extend({
        val: function (e) {
            var t, n, r, i = this[0];
            {
                if (arguments.length) return r = se.isFunction(e), this.each(function (n) {
                    var i;
                    1 === this.nodeType && (i = r ? e.call(this, n, se(this).val()) : e, null == i ? i = "" : "number" == typeof i ? i += "" : se.isArray(i) && (i = se.map(i, function (e) {
                        return null == e ? "" : e + ""
                    })), t = se.valHooks[this.type] || se.valHooks[this.nodeName.toLowerCase()], t && "set" in t && void 0 !== t.set(this, i, "value") || (this.value = i))
                });
                if (i) return t = se.valHooks[i.type] || se.valHooks[i.nodeName.toLowerCase()], t && "get" in t && void 0 !== (n = t.get(i, "value")) ? n : (n = i.value, "string" == typeof n ? n.replace(ct, "") : null == n ? "" : n)
            }
        }
    }), se.extend({
        valHooks: {
            option: {
                get: function (e) {
                    return se.trim(e.value)
                }
            }, select: {
                get: function (e) {
                    for (var t, n, r = e.options, i = e.selectedIndex, o = "select-one" === e.type || 0 > i, s = o ? null : [], a = o ? i + 1 : r.length, u = 0 > i ? a : o ? i : 0; a > u; u++) if (n = r[u], (n.selected || u === i) && (ie.optDisabled ? !n.disabled : null === n.getAttribute("disabled")) && (!n.parentNode.disabled || !se.nodeName(n.parentNode, "optgroup"))) {
                        if (t = se(n).val(), o) return t;
                        s.push(t)
                    }
                    return s
                }, set: function (e, t) {
                    for (var n, r, i = e.options, o = se.makeArray(t), s = i.length; s--;) r = i[s], (r.selected = se.inArray(se.valHooks.option.get(r), o) > -1) && (n = !0);
                    return n || (e.selectedIndex = -1), o
                }
            }
        }
    }), se.each(["radio", "checkbox"], function () {
        se.valHooks[this] = {
            set: function (e, t) {
                return se.isArray(t) ? e.checked = se.inArray(se(e).val(), t) > -1 : void 0
            }
        }, ie.checkOn || (se.valHooks[this].get = function (e) {
            return null === e.getAttribute("value") ? "on" : e.value
        })
    }), se.each("blur focus focusin focusout resize scroll click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave change select submit keydown keypress keyup contextmenu".split(" "), function (e, t) {
        se.fn[t] = function (e, n) {
            return arguments.length > 0 ? this.on(t, null, e, n) : this.trigger(t)
        }
    }), se.fn.extend({
        hover: function (e, t) {
            return this.mouseenter(e).mouseleave(t || e)
        }, bind: function (e, t, n) {
            return this.on(e, null, t, n)
        }, unbind: function (e, t) {
            return this.off(e, null, t)
        }, delegate: function (e, t, n, r) {
            return this.on(t, e, n, r)
        }, undelegate: function (e, t, n) {
            return 1 === arguments.length ? this.off(e, "**") : this.off(t, e || "**", n)
        }
    });
    var ft = e.location, pt = se.now(), dt = /\?/;
    se.parseJSON = function (e) {
        return JSON.parse(e + "")
    }, se.parseXML = function (t) {
        var n;
        if (!t || "string" != typeof t) return null;
        try {
            n = (new e.DOMParser).parseFromString(t, "text/xml")
        } catch (r) {
            n = void 0
        }
        return n && !n.getElementsByTagName("parsererror").length || se.error("Invalid XML: " + t), n
    };
    var ht = /#.*$/, gt = /([?&])_=[^&]*/, mt = /^(.*?):[ \t]*([^\r\n]*)$/gm,
        vt = /^(?:about|app|app-storage|.+-extension|file|res|widget):$/, yt = /^(?:GET|HEAD)$/, xt = /^\/\//, bt = {},
        wt = {}, Tt = "*/".concat("*"), Ct = Q.createElement("a");
    Ct.href = ft.href, se.extend({
        active: 0,
        lastModified: {},
        etag: {},
        ajaxSettings: {
            url: ft.href,
            type: "GET",
            isLocal: vt.test(ft.protocol),
            global: !0,
            processData: !0,
            async: !0,
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            accepts: {
                "*": Tt,
                text: "text/plain",
                html: "text/html",
                xml: "application/xml, text/xml",
                json: "application/json, text/javascript"
            },
            contents: {xml: /xml/, html: /html/, json: /json/},
            responseFields: {xml: "responseXML", text: "responseText", json: "responseJSON"},
            converters: {"* text": String, "text html": !0, "text json": se.parseJSON, "text xml": se.parseXML},
            flatOptions: {url: !0, context: !0}
        },
        ajaxSetup: function (e, t) {
            return t ? X(X(e, se.ajaxSettings), t) : X(se.ajaxSettings, e)
        },
        ajaxPrefilter: B(bt),
        ajaxTransport: B(wt),
        ajax: function (t, n) {
            function r(t, n, r, a) {
                var l, f, y, x, w, C = n;
                2 !== b && (b = 2, u && e.clearTimeout(u), i = void 0, s = a || "", T.readyState = t > 0 ? 4 : 0, l = t >= 200 && 300 > t || 304 === t, r && (x = z(p, T, r)), x = U(p, x, T, l), l ? (p.ifModified && (w = T.getResponseHeader("Last-Modified"), w && (se.lastModified[o] = w), w = T.getResponseHeader("etag"), w && (se.etag[o] = w)), 204 === t || "HEAD" === p.type ? C = "nocontent" : 304 === t ? C = "notmodified" : (C = x.state, f = x.data, y = x.error, l = !y)) : (y = C, !t && C || (C = "error", 0 > t && (t = 0))), T.status = t, T.statusText = (n || C) + "", l ? g.resolveWith(d, [f, C, T]) : g.rejectWith(d, [T, C, y]), T.statusCode(v), v = void 0, c && h.trigger(l ? "ajaxSuccess" : "ajaxError", [T, p, l ? f : y]), m.fireWith(d, [T, C]), c && (h.trigger("ajaxComplete", [T, p]), --se.active || se.event.trigger("ajaxStop")))
            }

            "object" == typeof t && (n = t, t = void 0), n = n || {};
            var i, o, s, a, u, l, c, f, p = se.ajaxSetup({}, n), d = p.context || p,
                h = p.context && (d.nodeType || d.jquery) ? se(d) : se.event, g = se.Deferred(),
                m = se.Callbacks("once memory"), v = p.statusCode || {}, y = {}, x = {}, b = 0, w = "canceled", T = {
                    readyState: 0, getResponseHeader: function (e) {
                        var t;
                        if (2 === b) {
                            if (!a) for (a = {}; t = mt.exec(s);) a[t[1].toLowerCase()] = t[2];
                            t = a[e.toLowerCase()]
                        }
                        return null == t ? null : t
                    }, getAllResponseHeaders: function () {
                        return 2 === b ? s : null
                    }, setRequestHeader: function (e, t) {
                        var n = e.toLowerCase();
                        return b || (e = x[n] = x[n] || e, y[e] = t), this
                    }, overrideMimeType: function (e) {
                        return b || (p.mimeType = e), this
                    }, statusCode: function (e) {
                        var t;
                        if (e) if (2 > b) for (t in e) v[t] = [v[t], e[t]]; else T.always(e[T.status]);
                        return this
                    }, abort: function (e) {
                        var t = e || w;
                        return i && i.abort(t), r(0, t), this
                    }
                };
            if (g.promise(T), p.url = ((t || p.url || ft.href) + "").replace(ht, "").replace(xt, ft.protocol + "//"), p.type = n.method || n.type || p.method || p.type, p.dataTypes = se.trim(p.dataType || "*").toLowerCase().match(be) || [""], null == p.crossDomain) {
                l = Q.createElement("a");
                try {
                    l.href = p.url, l.href = l.href, p.crossDomain = Ct.protocol + "//" + Ct.host != l.protocol + "//" + l.host
                } catch (C) {
                    p.crossDomain = !0
                }
            }
            if (p.data && p.processData && "string" != typeof p.data && (p.data = se.param(p.data, p.traditional)), _(bt, p, n, T), 2 === b) return T;
            c = se.event && p.global, c && 0 === se.active++ && se.event.trigger("ajaxStart"), p.type = p.type.toUpperCase(), p.hasContent = !yt.test(p.type), o = p.url, p.hasContent || (p.data && (o = p.url += (dt.test(o) ? "&" : "?") + p.data, delete p.data), p.cache === !1 && (p.url = gt.test(o) ? o.replace(gt, "$1_=" + pt++) : o + (dt.test(o) ? "&" : "?") + "_=" + pt++)), p.ifModified && (se.lastModified[o] && T.setRequestHeader("If-Modified-Since", se.lastModified[o]), se.etag[o] && T.setRequestHeader("If-None-Match", se.etag[o])), (p.data && p.hasContent && p.contentType !== !1 || n.contentType) && T.setRequestHeader("Content-Type", p.contentType), T.setRequestHeader("Accept", p.dataTypes[0] && p.accepts[p.dataTypes[0]] ? p.accepts[p.dataTypes[0]] + ("*" !== p.dataTypes[0] ? ", " + Tt + "; q=0.01" : "") : p.accepts["*"]);
            for (f in p.headers) T.setRequestHeader(f, p.headers[f]);
            if (p.beforeSend && (p.beforeSend.call(d, T, p) === !1 || 2 === b)) return T.abort();
            if (w = "abort", m.add(p.complete), T.done(p.success), T.fail(p.error), i = _(wt, p, n, T)) {
                if (T.readyState = 1, c && h.trigger("ajaxSend", [T, p]), 2 === b) return T;
                p.async && p.timeout > 0 && (u = e.setTimeout(function () {
                    T.abort("timeout")
                }, p.timeout));
                try {
                    b = 1, i.send(y, r)
                } catch (C) {
                    if (!(2 > b)) throw C;
                    r(-1, C)
                }
            } else r(-1, "No Transport");
            return T
        },
        getJSON: function (e, t, n) {
            return se.get(e, t, n, "json")
        },
        getScript: function (e, t) {
            return se.get(e, void 0, t, "script")
        }
    }), se.each(["get", "post"], function (e, t) {
        se[t] = function (e, n, r, i) {
            return se.isFunction(n) && (i = i || r, r = n, n = void 0), se.ajax(se.extend({
                url: e,
                type: t,
                dataType: i,
                data: n,
                success: r
            }, se.isPlainObject(e) && e))
        }
    }), se._evalUrl = function (e) {
        return se.ajax({url: e, type: "GET", dataType: "script", cache: !0, async: !1, global: !1, "throws": !0})
    }, se.fn.extend({
        wrapAll: function (e) {
            var t;
            return this[0] && (se.isFunction(e) && (e = e.call(this[0])), t = se(e, this[0].ownerDocument).eq(0).clone(!0), this[0].parentNode && t.insertBefore(this[0]), t.map(function () {
                for (var e = this; e.firstElementChild;) e = e.firstElementChild;
                return e
            }).append(this)), this
        }, wrapInner: function (e) {
            return se.isFunction(e) ? this.each(function (t) {
                se(this).wrapInner(e.call(this, t))
            }) : this.each(function () {
                var t = se(this), n = t.contents();
                n.length ? n.wrapAll(e) : t.append(e)
            })
        }, wrap: function (e) {
            var t = se.isFunction(e);
            return this.each(function (n) {
                se(this).wrapAll(t ? e.call(this, n) : e)
            })
        }, unwrap: function (e) {
            return this.parent(e).not("body").each(function () {
                se(this).replaceWith(this.childNodes)
            }), this
        }
    }), se.expr.filters.hidden = function (e) {
        return !se.expr.filters.visible(e)
    }, se.expr.filters.visible = function (e) {
        return !!(e.offsetWidth || e.offsetHeight || e.getClientRects().length)
    };
    var kt = /%20/g, Et = /\[\]$/, Dt = /\r?\n/g, Nt = /^(?:submit|button|image|reset|file)$/i,
        St = /^(?:input|select|textarea|keygen)/i;
    se.param = function (e, t) {
        var n, r = [], i = function (e, t) {
            t = se.isFunction(t) ? t() : null == t ? "" : t, r[r.length] = encodeURIComponent(e) + "=" + encodeURIComponent(t)
        };
        if (void 0 === t && (t = se.ajaxSettings && se.ajaxSettings.traditional), se.isArray(e) || e.jquery && !se.isPlainObject(e)) se.each(e, function () {
            i(this.name, this.value)
        }); else for (n in e) V(n, e[n], t, i);
        return r.join("&").replace(kt, "+")
    }, se.fn.extend({
        serialize: function () {
            return se.param(this.serializeArray())
        }, serializeArray: function () {
            return this.map(function () {
                var e = se.prop(this, "elements");
                return e ? se.makeArray(e) : this
            }).filter(function () {
                var e = this.type;
                return this.name && !se(this).is(":disabled") && St.test(this.nodeName) && !Nt.test(e) && (this.checked || !Le.test(e))
            }).map(function (e, t) {
                var n = se(this).val();
                return null == n ? null : se.isArray(n) ? se.map(n, function (e) {
                    return {name: t.name, value: e.replace(Dt, "\r\n")}
                }) : {name: t.name, value: n.replace(Dt, "\r\n")}
            }).get()
        }
    }), se.ajaxSettings.xhr = function () {
        try {
            return new e.XMLHttpRequest
        } catch (t) {
        }
    };
    var jt = {0: 200, 1223: 204}, At = se.ajaxSettings.xhr();
    ie.cors = !!At && "withCredentials" in At, ie.ajax = At = !!At, se.ajaxTransport(function (e) {
        var t;
        return ie.cors || At && !e.crossDomain ? {
            send: function (n, r) {
                var i, o = e.xhr();
                if (o.open(e.type, e.url, e.async, e.username, e.password), e.xhrFields) for (i in e.xhrFields) o[i] = e.xhrFields[i];
                e.mimeType && o.overrideMimeType && o.overrideMimeType(e.mimeType), e.crossDomain || n["X-Requested-With"] || (n["X-Requested-With"] = "XMLHttpRequest");
                for (i in n) o.setRequestHeader(i, n[i]);
                t = function (e) {
                    return function () {
                        t && (t = o.onload = o.onerror = null, "abort" === e ? o.abort() : "error" === e ? r(o.status, o.statusText) : r(jt[o.status] || o.status, o.statusText, "string" == typeof o.responseText ? {text: o.responseText} : void 0, o.getAllResponseHeaders()))
                    }
                }, o.onload = t(), o.onerror = t("error"), t = t("abort");
                try {
                    o.send(e.hasContent && e.data || null)
                } catch (s) {
                    if (t) throw s
                }
            }, abort: function () {
                t && t()
            }
        } : void 0
    }), se.ajaxSetup({
        accepts: {script: "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript"},
        contents: {script: /(?:java|ecma)script/},
        converters: {
            "text script": function (e) {
                return se.globalEval(e), e
            }
        }
    }), se.ajaxPrefilter("script", function (e) {
        void 0 === e.cache && (e.cache = !1), e.crossDomain && (e.type = "GET")
    }), se.ajaxTransport("script", function (e) {
        if (e.crossDomain) {
            var t, n;
            return {
                send: function (r, i) {
                    t = se("<script>").prop({charset: e.scriptCharset, src: e.url}).on("load error", n = function (e) {
                        t.remove(), n = null, e && i("error" === e.type ? 404 : 200, e.type)
                    }), Q.head.appendChild(t[0])
                }, abort: function () {
                    n && n()
                }
            }
        }
    });
    var qt = [], Lt = /(=)\?(?=&|$)|\?\?/;
    se.ajaxSetup({
        jsonp: "callback", jsonpCallback: function () {
            var e = qt.pop() || se.expando + "_" + pt++;
            return this[e] = !0, e
        }
    }), se.ajaxPrefilter("json jsonp", function (t, n, r) {
        var i, o, s,
            a = t.jsonp !== !1 && (Lt.test(t.url) ? "url" : "string" == typeof t.data && 0 === (t.contentType || "").indexOf("application/x-www-form-urlencoded") && Lt.test(t.data) && "data");
        return a || "jsonp" === t.dataTypes[0] ? (i = t.jsonpCallback = se.isFunction(t.jsonpCallback) ? t.jsonpCallback() : t.jsonpCallback, a ? t[a] = t[a].replace(Lt, "$1" + i) : t.jsonp !== !1 && (t.url += (dt.test(t.url) ? "&" : "?") + t.jsonp + "=" + i), t.converters["script json"] = function () {
            return s || se.error(i + " was not called"), s[0]
        }, t.dataTypes[0] = "json", o = e[i], e[i] = function () {
            s = arguments
        }, r.always(function () {
            void 0 === o ? se(e).removeProp(i) : e[i] = o, t[i] && (t.jsonpCallback = n.jsonpCallback, qt.push(i)), s && se.isFunction(o) && o(s[0]), s = o = void 0
        }), "script") : void 0
    }), ie.createHTMLDocument = function () {
        var e = Q.implementation.createHTMLDocument("").body;
        return e.innerHTML = "<form></form><form></form>", 2 === e.childNodes.length
    }(), se.parseHTML = function (e, t, n) {
        if ("string" != typeof e) return [];
        "boolean" == typeof t && (n = t, t = !1), t = t || (ie.createHTMLDocument ? Q.implementation.createHTMLDocument("") : Q);
        var r = de.exec(e), i = !n && [];
        return r ? [t.createElement(r[1])] : (r = g([e], t, i), i && i.length && se(i).remove(), se.merge([], r.childNodes))
    }, se.fn.load = function (e, t, n) {
        var r, i, o, s = this, a = e.indexOf(" ");
        return a > -1 && (r = se.trim(e.slice(a)), e = e.slice(0, a)), se.isFunction(t) ? (n = t, t = void 0) : t && "object" == typeof t && (i = "POST"), s.length > 0 && se.ajax({
            url: e,
            type: i || "GET",
            dataType: "html",
            data: t
        }).done(function (e) {
            o = arguments, s.html(r ? se("<div>").append(se.parseHTML(e)).find(r) : e)
        }).always(n && function (e, t) {
            s.each(function () {
                n.apply(s, o || [e.responseText, t, e])
            })
        }), this
    }, se.each(["ajaxStart", "ajaxStop", "ajaxComplete", "ajaxError", "ajaxSuccess", "ajaxSend"], function (e, t) {
        se.fn[t] = function (e) {
            return this.on(t, e)
        }
    }), se.expr.filters.animated = function (e) {
        return se.grep(se.timers, function (t) {
            return e === t.elem
        }).length
    }, se.offset = {
        setOffset: function (e, t, n) {
            var r, i, o, s, a, u, l, c = se.css(e, "position"), f = se(e), p = {};
            "static" === c && (e.style.position = "relative"), a = f.offset(), o = se.css(e, "top"), u = se.css(e, "left"), l = ("absolute" === c || "fixed" === c) && (o + u).indexOf("auto") > -1, l ? (r = f.position(), s = r.top, i = r.left) : (s = parseFloat(o) || 0, i = parseFloat(u) || 0), se.isFunction(t) && (t = t.call(e, n, se.extend({}, a))), null != t.top && (p.top = t.top - a.top + s), null != t.left && (p.left = t.left - a.left + i), "using" in t ? t.using.call(e, p) : f.css(p)
        }
    }, se.fn.extend({
        offset: function (e) {
            if (arguments.length) return void 0 === e ? this : this.each(function (t) {
                se.offset.setOffset(this, e, t)
            });
            var t, n, r, i, o = this[0];
            if (o) return o.getClientRects().length ? (r = o.getBoundingClientRect(), r.width || r.height ? (i = o.ownerDocument, n = Y(i), t = i.documentElement, {
                top: r.top + n.pageYOffset - t.clientTop,
                left: r.left + n.pageXOffset - t.clientLeft
            }) : r) : {top: 0, left: 0}
        }, position: function () {
            if (this[0]) {
                var e, t, n = this[0], r = {top: 0, left: 0};
                return "fixed" === se.css(n, "position") ? t = n.getBoundingClientRect() : (e = this.offsetParent(), t = this.offset(), se.nodeName(e[0], "html") || (r = e.offset()), r.top += se.css(e[0], "borderTopWidth", !0) - e.scrollTop(), r.left += se.css(e[0], "borderLeftWidth", !0) - e.scrollLeft()), {
                    top: t.top - r.top - se.css(n, "marginTop", !0),
                    left: t.left - r.left - se.css(n, "marginLeft", !0)
                }
            }
        }, offsetParent: function () {
            return this.map(function () {
                for (var e = this.offsetParent; e && "static" === se.css(e, "position");) e = e.offsetParent;
                return e || Ge
            })
        }
    }), se.each({scrollLeft: "pageXOffset", scrollTop: "pageYOffset"}, function (e, t) {
        var n = "pageYOffset" === t;
        se.fn[e] = function (r) {
            return Te(this, function (e, r, i) {
                var o = Y(e);
                return void 0 === i ? o ? o[t] : e[r] : void(o ? o.scrollTo(n ? o.pageXOffset : i, n ? i : o.pageYOffset) : e[r] = i)
            }, e, r, arguments.length, null)
        }
    }), se.each(["top", "left"], function (e, t) {
        se.cssHooks[t] = S(ie.pixelPosition, function (e, n) {
            return n ? (n = N(e, t), Ve.test(n) ? se(e).position()[t] + "px" : n) : void 0
        })
    }), se.each({Height: "height", Width: "width"}, function (e, t) {
        se.each({padding: "inner" + e, content: t, "": "outer" + e}, function (n, r) {
            se.fn[r] = function (r, i) {
                var o = arguments.length && (n || "boolean" != typeof r),
                    s = n || (r === !0 || i === !0 ? "margin" : "border");
                return Te(this, function (t, n, r) {
                    var i;
                    return se.isWindow(t) ? t.document.documentElement["client" + e] : 9 === t.nodeType ? (i = t.documentElement, Math.max(t.body["scroll" + e], i["scroll" + e], t.body["offset" + e], i["offset" + e], i["client" + e])) : void 0 === r ? se.css(t, n, s) : se.style(t, n, r, s)
                }, t, o ? r : void 0, o, null)
            }
        })
    }), "function" == typeof define && define.amd && define("jquery", [], function () {
        return se
    });
    var Ht = e.jQuery, Ft = e.$;
    return se.noConflict = function (t) {
        return e.$ === se && (e.$ = Ft), t && e.jQuery === se && (e.jQuery = Ht), se
    }, t || (e.jQuery = e.$ = se), se
});