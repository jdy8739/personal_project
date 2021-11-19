/*
 * v-dragged v0.0.5
 * https://github.com/zhanziyang/v-dragged
 * 
 * Copyright (c) 2017 zhanziyang
 * Released under the ISC license
 */
  
(function (global, factory) {
	typeof exports === 'object' && typeof module !== 'undefined' ? module.exports = factory() :
	//typeof define === 'function' && define.amd ? define(factory) :
	(global.VDragged = factory());
}(this, (function () { 'use strict';

/*
object-assign
(c) Sindre Sorhus
@license MIT
*/

/* eslint-disable no-unused-vars */
var getOwnPropertySymbols = Object.getOwnPropertySymbols;
var hasOwnProperty = Object.prototype.hasOwnProperty;
var propIsEnumerable = Object.prototype.propertyIsEnumerable;

function toObject(val) {
	if (val === null || val === undefined) {
		throw new TypeError('Object.assign cannot be called with null or undefined');
	}

	return Object(val);
}

function shouldUseNative() {
	try {
		if (!Object.assign) {
			return false;
		}

		// Detect buggy property enumeration order in older V8 versions.

		// https://bugs.chromium.org/p/v8/issues/detail?id=4118
		var test1 = new String('abc');  // eslint-disable-line no-new-wrappers
		test1[5] = 'de';
		if (Object.getOwnPropertyNames(test1)[0] === '5') {
			return false;
		}

		// https://bugs.chromium.org/p/v8/issues/detail?id=3056
		var test2 = {};
		for (var i = 0; i < 10; i++) {
			test2['_' + String.fromCharCode(i)] = i;
		}
		var order2 = Object.getOwnPropertyNames(test2).map(function (n) {
			return test2[n];
		});
		if (order2.join('') !== '0123456789') {
			return false;
		}

		// https://bugs.chromium.org/p/v8/issues/detail?id=3056
		var test3 = {};
		'abcdefghijklmnopqrst'.split('').forEach(function (letter) {
			test3[letter] = letter;
		});
		if (Object.keys(Object.assign({}, test3)).join('') !==
				'abcdefghijklmnopqrst') {
			return false;
		}

		return true;
	} catch (err) {
		// We don't expect any of the above to throw, but better to be safe.
		return false;
	}
}

var index = shouldUseNative() ? Object.assign : function (target, source) {
	var from;
	var to = toObject(target);
	var symbols;

	for (var s = 1; s < arguments.length; s++) {
		from = Object(arguments[s]);

		for (var key in from) {
			if (hasOwnProperty.call(from, key)) {
				to[key] = from[key];
			}
		}

		if (getOwnPropertySymbols) {
			symbols = getOwnPropertySymbols(from);
			for (var i = 0; i < symbols.length; i++) {
				if (propIsEnumerable.call(from, symbols[i])) {
					to[symbols[i]] = from[symbols[i]];
				}
			}
		}
	}

	return to;
};

var u = {
  addEventListeners: function addEventListeners(el, events, handler) {
    for (var i = 0, len = events.length; i < len; i++) {
      el.addEventListener(events[i], handler);
    }
  },
  removeEventListeners: function removeEventListeners(el, events, handler) {
    for (var i = 0, len = events.length; i < len; i++) {
      el.removeEventListener(events[i], handler);
    }
  },
  getPointer: function getPointer(evt) {
    var pointer = void 0;
    if (evt.touches && evt.touches[0]) {
      pointer = evt.touches[0];
    } else if (evt.changedTouches && evt.changedTouches[0]) {
      pointer = evt.changedTouches[0];
    } else {
      pointer = evt;
    }
    return pointer;
  }
};

var POINTER_START_EVENTS = ['mousedown', 'touchstart'];
var POINTER_MOVE_EVENTS = ['mousemove', 'touchmove'];
var POINTER_END_EVENTS = ['mouseup', 'touchend'];
var draggedElem;

var directive = {
  inserted: function inserted(el, binding, vnode) {
    if (!document) return;
    var prevent = !!binding.modifiers.prevent;
    function onPointerStart(evt) {
      prevent && evt.preventDefault();
      var pointer = u.getPointer(evt);
      el.lastCoords = el.firstCoords = {
        x: pointer.clientX,
        y: pointer.clientY
      };
      binding.value({
        el: el,
        first: true,
        clientX: pointer.clientX,
        clientY: pointer.clientY
      });
      draggedElem = el;
    }
    function onPointerEnd(evt) {
      prevent && evt.preventDefault();
      if (el !== draggedElem) return;
      el.lastCoords = null;
      var pointer = u.getPointer(evt);
      binding.value({
        el: el,
        last: true,
        clientX: pointer.clientX,
        clientY: pointer.clientY
      });
      draggedElem = null;
    }
    function onPointerMove(evt) {
      prevent && evt.preventDefault();
      if (el !== draggedElem) return;
      var pointer = u.getPointer(evt);
      if (el.lastCoords) {
        var deltaX = pointer.clientX - el.lastCoords.x;
        var deltaY = pointer.clientY - el.lastCoords.y;
        var offsetX = pointer.clientX - el.firstCoords.x;
        var offsetY = pointer.clientY - el.firstCoords.y;
        var clientX = pointer.clientX;
        var clientY = pointer.clientY;

        binding.value({
          el: el,
          deltaX: deltaX,
          deltaY: deltaY,
          offsetX: offsetX,
          offsetY: offsetY,
          clientX: clientX,
          clientY: clientY
        });
        el.lastCoords = {
          x: pointer.clientX,
          y: pointer.clientY
        };
      }
    }
    u.addEventListeners(el, POINTER_START_EVENTS, onPointerStart);
    u.addEventListeners(document.documentElement, POINTER_END_EVENTS, onPointerEnd);
    u.addEventListeners(document.documentElement, POINTER_MOVE_EVENTS, onPointerMove);
  },
  unbind: function unbind(el) {
    u.removeEventListeners(el, POINTER_START_EVENTS);
    u.removeEventListeners(document.documentElement, POINTER_END_EVENTS);
    u.removeEventListeners(document.documentElement, POINTER_MOVE_EVENTS);
  }
};

var defaultOptions = {};

var VDragged = {
  install: function install(Vue, options) {
    options = index({}, defaultOptions, options);
    var major = Number(Vue.version.split('.')[0]);
    var minor = Number(Vue.version.split('.')[1]);
    if (major < 2 && minor < 1) {
      throw new Error('v-dragged supports vue version 2.1 and above. You are using Vue@' + Vue.version + '. Please upgrade to the latest version of Vue.');
    }
    // registration
    Vue.directive('dragged', directive);
  },

  directive: directive
};

return VDragged;

})));
