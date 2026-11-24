package com.example

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.TypedValue
import android.view.Gravity
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnectionWrapper
import android.view.inputmethod.InputMethodManager
import android.webkit.CookieManager
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatEditText
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AspectRatio
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.os.LocaleListCompat
import com.example.ui.theme.AmberAccent
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.NavyPrimary
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.Locale

private const val TARGET_URL = "https://s4bayi.erciyes.com"
private const val LOGIN_ZOOM_PERCENT = 150
private const val DEFAULT_PAGE_ZOOM = 100
private const val AUTO_SISTEM = "RTP"
private const val AUTO_UST_BIRIM = "100"
private const val AUTO_USERNAME = "5155rf01"
private const val AUTO_PASSWORD = "ab4321"
private const val RF_YUKLEME_SICIL = "01"
private const val RF_YUKLEME_NAME = "MUHAMMED YILMAZ"
private const val RF_PLAKA = "61 BV 092"
private const val RF_SOFOR_ADI = "MUHAMMED YILMAZ"
private const val RF_TELF = "05372099461"
private const val PREFS_NAME = "s4bayi_prefs"
private const val KEY_XX_SERIAL_COUNTER = "xx_serial_counter"
private const val KEY_EKSIK_ITEMS_JSON = "eksik_items_json"
private const val INITIAL_XX_SERIAL = 123456789012L

data class EksikProductItem(
  val code: String,
  val qtyText: String = "",
  val totalQty: Int = 1,
  val scannedCount: Int = 0,
  val description: String = ""
) {
  val remainingQty: Int get() = (totalQty - scannedCount).coerceAtLeast(0)
  val isFullyScanned: Boolean get() = totalQty > 0 && scannedCount >= totalQty
}

private fun parseQtyFromText(qtyText: String): Int {
  val numStr = Regex("^(\\d+(?:[.,]\\d+)?)").find(qtyText.trim())?.groupValues?.get(1) ?: return 1
  return numStr.replace(',', '.').toDoubleOrNull()?.toInt()?.coerceAtLeast(1) ?: 1
}

private fun formatQtyBadge(qty: Int, rawQtyText: String): String {
  val rawUnit = rawQtyText.replace(Regex("^[0-9.,\\s]+"), "").trim().uppercase(Locale.US)
  val normalizedUnit = when {
    rawUnit.isEmpty() || rawUnit == "ADT" || rawUnit == "ADET" || rawUnit == "AD" -> "ADET"
    else -> rawUnit
  }
  return String.format(Locale.US, "%d %s", qty, normalizedUnit)
}

private fun parseEksikItemsJson(rawJson: String?): List<EksikProductItem> {
  if (rawJson.isNullOrBlank() || rawJson == "null") return emptyList()
  val result = mutableListOf<EksikProductItem>()
  try {
    val unescaped = if (rawJson.startsWith("\"") && rawJson.endsWith("\"")) {
      JSONObject("{\"v\":$rawJson}").getString("v")
    } else {
      rawJson
    }
    val arr = JSONArray(unescaped)
    val seen = HashSet<String>()
    for (i in 0 until arr.length()) {
      val obj = arr.optJSONObject(i) ?: continue
      val code = obj.optString("code", "").trim()
      if (code.isEmpty() || !seen.add(code)) continue
      val qtyText = obj.optString("qtyText", "").trim()
      val totalQty = if (obj.has("totalQty")) obj.optInt("totalQty", 1).coerceAtLeast(1) else parseQtyFromText(qtyText)
      val scannedCount = obj.optInt("scannedCount", 0).coerceAtLeast(0)
      val desc = obj.optString("description", "").trim()
      result.add(
        EksikProductItem(
          code = code,
          qtyText = qtyText,
          totalQty = totalQty,
          scannedCount = scannedCount,
          description = desc
        )
      )
    }
  } catch (_: Exception) {}
  return result
}

private fun serializeEksikItems(items: List<EksikProductItem>): String {
  val arr = JSONArray()
  items.forEach { item ->
    arr.put(
      JSONObject().apply {
        put("code", item.code)
        put("qtyText", item.qtyText)
        put("totalQty", item.totalQty)
        put("scannedCount", item.scannedCount)
        put("description", item.description)
      }
    )
  }
  return arr.toString()
}

class PlakaAwareWebView(context: Context) : WebView(context) {
  @Volatile
  var isPlakaFocused: Boolean = false
  private var lastConnectionWasPlaka: Boolean = false

  fun updatePlakaFocus(focused: Boolean) {
    val changed = (isPlakaFocused != focused)
    isPlakaFocused = focused
    if (changed || (focused && !lastConnectionWasPlaka)) {
      post {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.restartInput(this)
      }
    }
  }

  private fun sendEnterKey(ic: InputConnection) {
    val now = SystemClock.uptimeMillis()
    ic.sendKeyEvent(
      KeyEvent(
        now,
        now,
        KeyEvent.ACTION_DOWN,
        KeyEvent.KEYCODE_ENTER,
        0,
        0,
        KeyCharacterMap.VIRTUAL_KEYBOARD,
        0,
        KeyEvent.FLAG_SOFT_KEYBOARD or KeyEvent.FLAG_KEEP_TOUCH_MODE
      )
    )
    ic.sendKeyEvent(
      KeyEvent(
        now,
        now,
        KeyEvent.ACTION_UP,
        KeyEvent.KEYCODE_ENTER,
        0,
        0,
        KeyCharacterMap.VIRTUAL_KEYBOARD,
        0,
        KeyEvent.FLAG_SOFT_KEYBOARD or KeyEvent.FLAG_KEEP_TOUCH_MODE
      )
    )
  }

  override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection? {
    val ic = super.onCreateInputConnection(outAttrs) ?: return null
    lastConnectionWasPlaka = isPlakaFocused
    if (isPlakaFocused) {
      outAttrs.imeOptions =
        (outAttrs.imeOptions and EditorInfo.IME_MASK_ACTION.inv()) or EditorInfo.IME_ACTION_UNSPECIFIED
      outAttrs.imeOptions = outAttrs.imeOptions and EditorInfo.IME_FLAG_NO_ENTER_ACTION.inv()
      outAttrs.inputType = outAttrs.inputType and InputType.TYPE_TEXT_FLAG_MULTI_LINE.inv()
    }
    return object : InputConnectionWrapper(ic, true) {
      override fun performEditorAction(editorAction: Int): Boolean {
        if (isPlakaFocused) {
          sendEnterKey(ic)
          return true
        }
        return super.performEditorAction(editorAction)
      }

      override fun commitText(text: CharSequence?, newCursorPosition: Int): Boolean {
        if (isPlakaFocused && text != null && (text.toString() == "\n" || text.toString() == "\r\n")) {
          sendEnterKey(ic)
          return true
        }
        return super.commitText(text, newCursorPosition)
      }
    }
  }
}

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        S4BayiBrowserApp()
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun S4BayiBrowserApp() {
  var webViewRef by remember { mutableStateOf<WebView?>(null) }
  var zoomPercent by remember { mutableIntStateOf(LOGIN_ZOOM_PERCENT) }
  var isLoading by remember { mutableStateOf(true) }
  var progress by remember { mutableFloatStateOf(0f) }
  var canGoBack by remember { mutableStateOf(false) }
  var canGoForward by remember { mutableStateOf(false) }
  var hasError by remember { mutableStateOf(false) }
  var errorMessage by remember { mutableStateOf("") }
  var currentUrl by remember { mutableStateOf(TARGET_URL) }
  var pageTitle by remember { mutableStateOf("") }
  var isDesktopMode by remember { mutableStateOf(false) }

  var showZoomSheet by remember { mutableStateOf(false) }
  var showMoreMenu by remember { mutableStateOf(false) }
  var showLanguageMenu by remember { mutableStateOf(false) }
  var showXxSheet by remember { mutableStateOf(false) }
  var eksikSearchQuery by remember { mutableStateOf("") }

  val defaultPageTitle = stringResource(R.string.default_page_title)
  val context = LocalContext.current
  val haptics = LocalHapticFeedback.current
  val prefs = remember { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }
  var nextXxSerial by remember {
    val saved = prefs.getLong(KEY_XX_SERIAL_COUNTER, INITIAL_XX_SERIAL)
    mutableLongStateOf(if (saved < INITIAL_XX_SERIAL) INITIAL_XX_SERIAL else saved)
  }
  var eksikItems by remember {
    mutableStateOf(parseEksikItemsJson(prefs.getString(KEY_EKSIK_ITEMS_JSON, null)))
  }

  val sheetState = rememberModalBottomSheetState()
  val scope = rememberCoroutineScope()

  @SuppressLint("ApplySharedPref")
  fun saveEksikItems(newItems: List<EksikProductItem>) {
    eksikItems = newItems
    prefs.edit().putString(KEY_EKSIK_ITEMS_JSON, serializeEksikItems(newItems)).commit()
  }

  fun updateEksikFromPage(rawItems: List<EksikProductItem>) {
    if (rawItems.isEmpty()) return
    val sameList = rawItems.size == eksikItems.size &&
      rawItems.indices.all { idx ->
        rawItems[idx].code == eksikItems[idx].code &&
          rawItems[idx].qtyText == eksikItems[idx].qtyText
      }
    if (!sameList) {
      saveEksikItems(rawItems)
    }
  }

  fun markEksikCodeScanned(code: String) {
    if (eksikItems.isEmpty()) return
    val idx = eksikItems.indexOfFirst { it.code.equals(code, ignoreCase = true) }
    if (idx >= 0) {
      val updated = eksikItems.toMutableList()
      val target = updated[idx]
      updated[idx] = target.copy(scannedCount = target.scannedCount + 1)
      saveEksikItems(updated)
    }
  }

  @SuppressLint("ApplySharedPref")
  fun commitNextXxSerial(nextVal: Long) {
    nextXxSerial = nextVal
    prefs.edit().putLong(KEY_XX_SERIAL_COUNTER, nextVal).commit()
  }

  fun copyBarcodeToClipboard(fullBarcode: String) {
    try {
      val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
      cm?.setPrimaryClip(ClipData.newPlainText("barcode", fullBarcode))
    } catch (_: Exception) {}
  }

  fun refreshEksikFromWebView(wv: WebView, onDone: () -> Unit) {
    val js = """
        (function() {
            var eksikList = [];
            var seenEksik = {};

            function isEksikDoc(doc) {
                if (!doc || !doc.body) return false;
                var norm = (doc.body.innerText || doc.body.textContent || '')
                    .replace(/[\s\u00A0]+/g, ' ')
                    .toLowerCase()
                    .replace(/\u0307/g, '');
                return norm.includes('eksik malzeme listesi') ||
                       (norm.includes('ileti metni') && norm.includes('iletitp'));
            }

            function parseEksikText(raw) {
                if (!raw) return;
                var lines = raw.split(/\r?\n/);
                for (var i = 0; i < lines.length; i++) {
                    var line = lines[i].replace(/\u00A0/g, ' ').replace(/\s+/g, ' ').trim();
                    if (!line) continue;
                    var m = line.match(/^(\d+(?:[.,]\d+)?\s*[A-Za-zÇĞİÖŞÜçğıöşü]{0,8})\s*-\s*([A-Za-z0-9_./]{5,32})(?:\s+(.*))?$/);
                    if (m) {
                        var qtyText = (m[1] || '').replace(/\s+/g, '').trim();
                        var code = (m[2] || '').trim();
                        var desc = (m[3] || '').trim();
                        if (code && !seenEksik[code]) {
                            seenEksik[code] = true;
                            eksikList.push({ code: code, qtyText: qtyText, description: desc });
                        }
                    }
                }
            }

            function scanDoc(doc) {
                if (!doc || !isEksikDoc(doc)) return;
                try {
                    var tds = doc.querySelectorAll('td');
                    for (var i = 0; i < tds.length; i++) {
                        if (tds[i].querySelector('table')) continue;
                        parseEksikText(tds[i].innerText || tds[i].textContent || '');
                    }
                    if (doc.body) {
                        parseEksikText(doc.body.innerText || doc.body.textContent || '');
                    }
                    var fields = doc.querySelectorAll('input[type="text"], textarea');
                    for (var j = 0; j < fields.length; j++) {
                        parseEksikText(fields[j].value || '');
                    }
                } catch (_) {}
            }

            scanDoc(document);
            var frames = document.querySelectorAll('iframe, frame');
            for (var f = 0; f < frames.length; f++) {
                try {
                    scanDoc(frames[f].contentDocument);
                } catch (_) {}
            }

            return JSON.stringify(eksikList);
        })();
    """.trimIndent()
    wv.evaluateJavascript(js) { rawResult ->
      try {
        val captured = parseEksikItemsJson(rawResult)
        if (captured.isNotEmpty()) {
          updateEksikFromPage(captured)
        }
      } catch (_: Exception) {}
      onDone()
    }
  }

  fun copyXxBarcodeOnly(rawProductCode: String) {
    val cleanCode = rawProductCode.trim().replace(Regex("\\s+"), "")
    if (cleanCode.isEmpty()) return
    val currentSerialNum = nextXxSerial
    val serial = String.format(Locale.US, "%012d", currentSerialNum)
    val fullBarcode = "XX$cleanCode-$serial"
    copyBarcodeToClipboard(fullBarcode)
    commitNextXxSerial(currentSerialNum + 1L)
    Toast.makeText(
      context,
      context.getString(R.string.xx_toast_copied_only, fullBarcode),
      Toast.LENGTH_SHORT
    ).show()
  }

  fun submitXxBarcodeToWebView(
    wv: WebView?,
    rawProductCode: String,
    pressEnter: Boolean,
    onSubmitted: () -> Unit = {}
  ) {
    val cleanCode = rawProductCode.trim().replace(Regex("\\s+"), "")
    if (cleanCode.isEmpty()) return
    val currentSerialNum = nextXxSerial
    val serial = String.format(Locale.US, "%012d", currentSerialNum)
    val fullBarcode = "XX$cleanCode-$serial"
    val quotedBarcode = JSONObject.quote(fullBarcode)

    if (wv == null) return

    val js = """
        (function() {
            function isEksikDoc(doc) {
                if (!doc || !doc.body) return false;
                var norm = (doc.body.innerText || doc.body.textContent || '')
                    .replace(/[\s\u00A0]+/g, ' ')
                    .toLowerCase()
                    .replace(/\u0307/g, '');
                return norm.includes('eksik malzeme listesi') ||
                       (norm.includes('ileti metni') && norm.includes('iletitp'));
            }

            function findScanInput(doc) {
                if (!doc || isEksikDoc(doc)) return null;
                var active = doc.activeElement;
                if (active && active.tagName === 'INPUT') {
                    var at = (active.type || 'text').toLowerCase();
                    if ((at === 'text' || at === 'search' || at === 'tel' || at === '') && !active.readOnly && !active.disabled) {
                        return active;
                    }
                }
                var inputs = doc.querySelectorAll('input');
                var fallback = null;
                for (var i = 0; i < inputs.length; i++) {
                    var inp = inputs[i];
                    var t = (inp.type || 'text').toLowerCase();
                    if (t !== 'text' && t !== 'search' && t !== 'tel' && t !== '') continue;
                    if (inp.readOnly || inp.disabled) continue;
                    try {
                        var cs = doc.defaultView ? doc.defaultView.getComputedStyle(inp) : null;
                        if (cs && cs.display === 'none') continue;
                        var bg = (cs ? cs.backgroundColor : '') || '';
                        if (bg.includes('255, 255') || bg.includes('255, 24') || bg.includes('255, 23') || (inp.style && inp.style.backgroundColor)) {
                            return inp;
                        }
                    } catch (_) {}
                    if (!fallback) fallback = inp;
                }
                return fallback;
            }

            var target = findScanInput(document);
            if (!target) {
                var frames = document.querySelectorAll('iframe, frame');
                for (var f = 0; f < frames.length; f++) {
                    try {
                        target = findScanInput(frames[f].contentDocument);
                        if (target) break;
                    } catch (_) {}
                }
            }
            if (target) {
                target.focus();
                target.value = $quotedBarcode;
                target.dispatchEvent(new Event('input', { bubbles: true }));
                target.dispatchEvent(new Event('change', { bubbles: true }));
                return true;
            }
            return false;
        })();
    """.trimIndent()

    wv.evaluateJavascript(js) { res ->
      if (res == "true") {
        commitNextXxSerial(currentSerialNum + 1L)
        markEksikCodeScanned(cleanCode)
        onSubmitted()
        wv.requestFocus()
        if (pressEnter) {
          wv.postDelayed({
            wv.requestFocus()
            val now = SystemClock.uptimeMillis()
            wv.dispatchKeyEvent(
              KeyEvent(
                now, now, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER,
                0, 0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0,
                KeyEvent.FLAG_SOFT_KEYBOARD or KeyEvent.FLAG_KEEP_TOUCH_MODE
              )
            )
            wv.dispatchKeyEvent(
              KeyEvent(
                now, now, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER,
                0, 0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0,
                KeyEvent.FLAG_SOFT_KEYBOARD or KeyEvent.FLAG_KEEP_TOUCH_MODE
              )
            )
          }, 120)
        }
      }
    }
  }

  // Helper function to apply zoom safely
  fun applyZoom(wv: WebView, percent: Int) {
    try {
      wv.settings.textZoom = percent
      wv.setInitialScale(percent)
      val scaleFactor = percent / 100.0
      val js = """
          (function() {
              document.body.style.zoom = '${percent}%';
              var meta = document.querySelector('meta[name=viewport]');
              if (!meta) {
                  meta = document.createElement('meta');
                  meta.name = 'viewport';
                  document.getElementsByTagName('head')[0].appendChild(meta);
              }
              meta.content = 'width=device-width, initial-scale=${scaleFactor}, minimum-scale=0.5, maximum-scale=10.0, user-scalable=yes';
          })();
      """.trimIndent()
      wv.evaluateJavascript(js, null)
    } catch (_: Exception) {}
  }

  // Dynamic JS processor for auto-fill
  fun processPage(wv: WebView, forceFill: Boolean = false) {
    val js = """
        (function() {
            function evaluatePage() {
                var passField = document.querySelector('input[type="password"], input[name*="parola"], input[id*="parola"], input[name*="sifre"], input[id*="sifre"], input[placeholder*="parola"], input[placeholder*="şifre"]');
                var isLoginPage = (passField !== null);
                var targetZoom = isLoginPage ? $LOGIN_ZOOM_PERCENT : $DEFAULT_PAGE_ZOOM;

                // 1. Apply zoom to document body & viewport meta tag
                document.body.style.zoom = targetZoom + '%';
                var meta = document.querySelector('meta[name=viewport]');
                if (!meta) {
                    meta = document.createElement('meta');
                    meta.name = 'viewport';
                    document.getElementsByTagName('head')[0].appendChild(meta);
                }
                var scale = targetZoom / 100.0;
                meta.content = 'width=device-width, initial-scale=' + scale + ', minimum-scale=0.5, maximum-scale=10.0, user-scalable=yes';

                // 2. Notify Android native bridge
                if (window.AndroidBridge && window.AndroidBridge.notifyPageState) {
                    window.AndroidBridge.notifyPageState(isLoginPage);
                }

                // 3. Setup Plaka Enter-key detection on all documents/frames
                function isPlakaInput(el) {
                    if (!el || el.tagName !== 'INPUT') return false;
                    var type = (el.type || 'text').toLowerCase();
                    if (type !== 'text' && type !== 'search' && type !== 'tel' && type !== '') return false;

                    var name = (el.name || '').toLowerCase();
                    var id = (el.id || '').toLowerCase();
                    var placeholder = (el.placeholder || '').toLowerCase();
                    var aria = (el.getAttribute('aria-label') || '').toLowerCase();
                    if (name.includes('plaka') || id.includes('plaka') || placeholder.includes('plaka') || aria.includes('plaka')) {
                        return true;
                    }

                    var doc = el.ownerDocument || document;
                    if (el.id) {
                        try {
                            var lbl = doc.querySelector('label[for="' + el.id.replace(/"/g, '\\"') + '"]');
                            if (lbl && (lbl.innerText || lbl.textContent || '').toLowerCase().includes('plaka')) {
                                return true;
                            }
                        } catch (_) {}
                    }

                    // Walk backwards through preceding siblings to find the closest label text before this input
                    var node = el.previousSibling;
                    var steps = 0;
                    while (node && steps < 8) {
                        if (node.nodeType === 3) {
                            var t = (node.textContent || '').toLowerCase().trim();
                            if (t.length > 0) {
                                return t.includes('plaka');
                            }
                        } else if (node.nodeType === 1) {
                            var tag = node.tagName;
                            if (tag === 'INPUT' || tag === 'SELECT' || tag === 'TEXTAREA') break;
                            if (node.querySelector && node.querySelector('input, select, textarea')) break;
                            var et = (node.innerText || node.textContent || '').toLowerCase().trim();
                            if (et.length > 0) {
                                return et.includes('plaka');
                            }
                        }
                        node = node.previousSibling;
                        steps++;
                    }

                    // Check immediate container / table structure without crossing previous inputs
                    var container = el.closest('td, th, div, p, li');
                    if (container) {
                        var ownText = '';
                        for (var c = 0; c < container.childNodes.length; c++) {
                            var ch = container.childNodes[c];
                            if (ch === el || (ch.contains && ch.contains(el))) break;
                            if (ch.nodeType === 1 && (ch.tagName === 'INPUT' || ch.tagName === 'SELECT' || ch.tagName === 'TEXTAREA' || (ch.querySelector && ch.querySelector('input, select, textarea')))) {
                                ownText = '';
                                continue;
                            }
                            if (ch.nodeType === 3) ownText += ' ' + ch.textContent;
                            else if (ch.nodeType === 1) ownText += ' ' + (ch.innerText || ch.textContent || '');
                        }
                        if (ownText.toLowerCase().trim().includes('plaka')) return true;

                        var inputsInContainer = container.querySelectorAll('input, select, textarea').length;
                        if (inputsInContainer <= 1) {
                            var prevSib = container.previousElementSibling;
                            if (prevSib && !prevSib.querySelector('input, select, textarea')) {
                                var sibTxt = (prevSib.innerText || prevSib.textContent || '').toLowerCase().trim();
                                if (sibTxt.includes('plaka')) return true;
                            }
                            var tr = container.closest('tr');
                            if (tr && tr.previousElementSibling && !tr.previousElementSibling.querySelector('input, select, textarea')) {
                                var trTxt = (tr.previousElementSibling.innerText || tr.previousElementSibling.textContent || '').toLowerCase().trim();
                                if (trTxt.includes('plaka')) return true;
                            }
                        }
                    }

                    return false;
                }

                function isScanInputField(el) {
                    if (!el || el.tagName !== 'INPUT') return false;
                    var type = (el.type || 'text').toLowerCase();
                    if (type !== 'text' && type !== 'search' && type !== 'tel' && type !== '') return false;
                    if (el.readOnly || el.disabled) return false;
                    var doc = el.ownerDocument || document;
                    if (!doc.body) return false;
                    var bodyText = (doc.body.innerText || doc.body.textContent || '')
                        .replace(/[\s\u00A0]+/g, ' ')
                        .toLowerCase()
                        .replace(/\u0307/g, '');
                    return bodyText.includes('arac.bilg') ||
                           bodyText.includes('tesl.güncelle') ||
                           bodyText.includes('tesl.guncelle') ||
                           (bodyText.includes('pguf') && bodyText.includes('pgdn')) ||
                           (bodyText.includes('yükle') && bodyText.includes('çıkar')) ||
                           (bodyText.includes('yukle') && bodyText.includes('cikar'));
                }

                function isEnterTargetInput(el) {
                    return isPlakaInput(el) || isScanInputField(el);
                }

                function hookDocumentPlaka(doc) {
                    if (!doc) return;
                    try {
                        var inputs = doc.querySelectorAll('input');
                        for (var k = 0; k < inputs.length; k++) {
                            if (isEnterTargetInput(inputs[k])) {
                                inputs[k].setAttribute('enterkeyhint', 'enter');
                            }
                        }
                        if (!doc.__plakaEnterHooked) {
                            doc.__plakaEnterHooked = true;
                            doc.addEventListener('focusin', function(e) {
                                var target = e.target;
                                var focused = isEnterTargetInput(target);
                                if (focused) {
                                    target.setAttribute('enterkeyhint', 'enter');
                                }
                                if (window.AndroidBridge && window.AndroidBridge.setPlakaFocused) {
                                    window.AndroidBridge.setPlakaFocused(focused);
                                }
                            }, true);
                            doc.addEventListener('focusout', function() {
                                setTimeout(function() {
                                    var active = doc.activeElement;
                                    if (window.AndroidBridge && window.AndroidBridge.setPlakaFocused) {
                                        window.AndroidBridge.setPlakaFocused(isEnterTargetInput(active));
                                    }
                                }, 30);
                            }, true);
                        }
                    } catch (_) {}
                }

                hookDocumentPlaka(document);
                var frames = document.querySelectorAll('iframe, frame');
                for (var f = 0; f < frames.length; f++) {
                    try {
                        hookDocumentPlaka(frames[f].contentDocument);
                    } catch (_) {}
                }
                if (document.activeElement && document.activeElement.tagName === 'INPUT') {
                    if (window.AndroidBridge && window.AndroidBridge.setPlakaFocused) {
                        window.AndroidBridge.setPlakaFocused(isEnterTargetInput(document.activeElement));
                    }
                }

                // 4. Auto-fill Sevkiyat RF screen fields (5 fields), leaving Montör Sicil No empty
                function fillSevkiyatFields(doc) {
                    if (!doc || !doc.body) return;
                    var bodyText = (doc.body.innerText || doc.body.textContent || '')
                        .replace(/[\s\u00A0]+/g, ' ')
                        .toLowerCase()
                        .replace(/\u0307/g, '');
                    var isSevkiyatScreen = bodyText.includes('yüklemeyi') || bodyText.includes('yuklemeyi') ||
                                           (bodyText.includes('plaka') && (bodyText.includes('şoför') || bodyText.includes('sofor')));
                    if (!isSevkiyatScreen) return;

                    var forceFill = $forceFill;

                    function setRfValue(el, val) {
                        if (!el) return;
                        var currentVal = (el.value || '').trim();
                        if (!forceFill) {
                            if (el.dataset && el.dataset.s4Filled === '1' && currentVal !== '') return;
                            if (currentVal !== '') return;
                        }
                        var wasReadOnly = el.readOnly;
                        if (wasReadOnly) el.readOnly = false;
                        el.value = val;
                        if (wasReadOnly) el.readOnly = true;
                        if (el.dataset && doc.readyState === 'complete') {
                            el.dataset.s4Filled = '1';
                        }
                        el.dispatchEvent(new Event('input', { bubbles: true }));
                        el.dispatchEvent(new Event('change', { bubbles: true }));
                    }

                    var walker = doc.createTreeWalker(
                        doc.body,
                        NodeFilter.SHOW_TEXT | NodeFilter.SHOW_ELEMENT,
                        {
                            acceptNode: function(node) {
                                if (node.nodeType === 1) {
                                    var tag = (node.tagName || '').toUpperCase();
                                    if (tag === 'SCRIPT' || tag === 'STYLE' || tag === 'NOSCRIPT') {
                                        return NodeFilter.FILTER_REJECT;
                                    }
                                    if (tag === 'INPUT') {
                                        return NodeFilter.FILTER_ACCEPT;
                                    }
                                    return NodeFilter.FILTER_SKIP;
                                }
                                if (node.nodeType === 3) {
                                    var p = node.parentElement;
                                    if (p) {
                                        var pTag = (p.tagName || '').toUpperCase();
                                        if (pTag === 'SCRIPT' || pTag === 'STYLE' || pTag === 'NOSCRIPT' || pTag === 'BUTTON' || pTag === 'TITLE') {
                                            return NodeFilter.FILTER_REJECT;
                                        }
                                    }
                                    return NodeFilter.FILTER_ACCEPT;
                                }
                                return NodeFilter.FILTER_SKIP;
                            }
                        }
                    );

                    var labelBuffer = '';
                    var currentSection = '';
                    var sectionIndex = 0;
                    var node;

                    while ((node = walker.nextNode())) {
                        if (node.nodeType === 3) {
                            var txt = (node.textContent || '').replace(/[\s\u00A0]+/g, ' ').trim();
                            if (txt.length > 0) {
                                labelBuffer += ' ' + txt;
                                var norm = labelBuffer.replace(/[\s\u00A0]+/g, ' ').toLowerCase().replace(/\u0307/g, '');
                                if (norm.includes('yüklemeyi') || norm.includes('yuklemeyi') || norm.includes('yapan sicil')) {
                                    currentSection = 'yukleme';
                                    sectionIndex = 0;
                                } else if (norm.includes('montör') || norm.includes('montor')) {
                                    currentSection = 'montor';
                                    sectionIndex = 0;
                                } else if (norm.includes('şoför') || norm.includes('sofor')) {
                                    currentSection = 'sofor';
                                    sectionIndex = 0;
                                } else if (norm.includes('plaka')) {
                                    currentSection = 'plaka';
                                    sectionIndex = 0;
                                } else if (norm.includes('telf') || norm.includes('telefon')) {
                                    currentSection = 'telf';
                                    sectionIndex = 0;
                                }
                            }
                        } else if (node.nodeType === 1 && node.tagName === 'INPUT') {
                            var t = (node.type || 'text').toLowerCase();
                            if (t === 'hidden' || t === 'submit' || t === 'button' || t === 'reset' || t === 'image' || t === 'checkbox' || t === 'radio') {
                                continue;
                            }
                            try {
                                var cs = doc.defaultView ? doc.defaultView.getComputedStyle(node) : null;
                                if (cs && cs.display === 'none') continue;
                            } catch (_) {}

                            if (currentSection === 'yukleme') {
                                if (sectionIndex === 0) {
                                    setRfValue(node, '$RF_YUKLEME_SICIL');
                                } else if (sectionIndex === 1) {
                                    setRfValue(node, '$RF_YUKLEME_NAME');
                                }
                                sectionIndex++;
                            } else if (currentSection === 'plaka') {
                                if (sectionIndex === 0) {
                                    setRfValue(node, '$RF_PLAKA');
                                }
                                sectionIndex++;
                            } else if (currentSection === 'sofor') {
                                if (sectionIndex === 0) {
                                    setRfValue(node, '$RF_SOFOR_ADI');
                                }
                                sectionIndex++;
                            } else if (currentSection === 'telf') {
                                if (sectionIndex === 0) {
                                    setRfValue(node, '$RF_TELF');
                                }
                                sectionIndex++;
                            } else if (currentSection === 'montor') {
                                // Leave Montör Sicil No empty
                                sectionIndex++;
                            }
                            labelBuffer = '';
                        }
                    }
                }

                fillSevkiyatFields(document);
                for (var f2 = 0; f2 < frames.length; f2++) {
                    try {
                        fillSevkiyatFields(frames[f2].contentDocument);
                    } catch (_) {}
                }

                // 5. Auto-fill login credentials ONLY if on login page
                if (isLoginPage) {
                    var sistemVal = '$AUTO_SISTEM';
                    var ustBirimVal = '$AUTO_UST_BIRIM';
                    var userVal = '$AUTO_USERNAME';
                    var passVal = '$AUTO_PASSWORD';

                    function setFieldValue(el, val) {
                        if (!el) return;
                        var tag = (el.tagName || '').toLowerCase();
                        if (tag === 'select') {
                            var options = el.options || [];
                            var matched = false;
                            for (var i = 0; i < options.length; i++) {
                                var optText = (options[i].text || '').toLowerCase();
                                var optVal = (options[i].value || '').toLowerCase();
                                var target = val.toLowerCase();
                                if (optVal === target || optText === target || optText.includes(target)) {
                                    el.selectedIndex = i;
                                    matched = true;
                                    break;
                                }
                            }
                            if (!matched && options.length > 0) {
                                el.value = val;
                            }
                            el.dispatchEvent(new Event('change', { bubbles: true }));
                            return;
                        }
                        el.value = val;
                        el.dispatchEvent(new Event('input', { bubbles: true }));
                        el.dispatchEvent(new Event('change', { bubbles: true }));
                        el.dispatchEvent(new Event('blur', { bubbles: true }));
                    }

                    var elements = document.querySelectorAll('input, select, textarea');
                    var filledSistem = false;
                    var filledUstBirim = false;
                    var filledUser = false;
                    var filledPass = false;

                    for (var i = 0; i < elements.length; i++) {
                        var el = elements[i];
                        if (el.type === 'hidden' || el.type === 'submit' || el.type === 'button') continue;

                        var type = (el.type || '').toLowerCase();
                        var name = (el.name || '').toLowerCase();
                        var id = (el.id || '').toLowerCase();
                        var placeholder = (el.placeholder || '').toLowerCase();
                        var labelAttr = (el.getAttribute('aria-label') || '').toLowerCase();
                        
                        var parentText = '';
                        if (el.parentElement) {
                            parentText = (el.parentElement.innerText || '').toLowerCase();
                            if (el.parentElement.parentElement) {
                                parentText += ' ' + (el.parentElement.parentElement.innerText || '').toLowerCase();
                            }
                        }

                        var labelText = '';
                        if (el.id) {
                            var lbl = document.querySelector('label[for="' + el.id + '"]');
                            if (lbl) labelText = (lbl.innerText || '').toLowerCase();
                        }

                        var text = name + ' ' + id + ' ' + placeholder + ' ' + labelAttr + ' ' + labelText + ' ' + parentText;

                        // 1. Sistem -> RTP
                        if (!filledSistem && (text.includes('sistem') || text.includes('system'))) {
                            setFieldValue(el, sistemVal);
                            filledSistem = true;
                        }
                        // 2. Üست birim -> 100
                        else if (!filledUstBirim && (text.includes('üst') || text.includes('ust') || text.includes('birim'))) {
                            setFieldValue(el, ustBirimVal);
                            filledUstBirim = true;
                        }
                        // 3. Parola -> ab4321
                        else if (!filledPass && (type === 'password' || text.includes('parola') || text.includes('şifre') || text.includes('sifre') || text.includes('pass'))) {
                            setFieldValue(el, passVal);
                            filledPass = true;
                        }
                        // 4. Kullanıcı -> 5155rf01
                        else if (!filledUser && (text.includes('kullan') || text.includes('user') || text.includes('login') || text.includes('bayi'))) {
                            setFieldValue(el, userVal);
                            filledUser = true;
                        }
                    }
                }

                // 6. Auto-capture product codes ONLY when on the Eksik (Eksik malzeme listesi) page
                (function captureEksikCodes() {
                    var items = [];
                    var seen = {};
                    var foundEksikScreen = false;

                    function parseLines(raw) {
                        if (!raw) return;
                        var lines = raw.split(/\r?\n/);
                        for (var i = 0; i < lines.length; i++) {
                            var line = lines[i].replace(/\u00A0/g, ' ').replace(/\s+/g, ' ').trim();
                            if (!line) continue;
                            var m = line.match(/^(\d+(?:[.,]\d+)?\s*[A-Za-zÇĞİÖŞÜçğıöşü]{0,8})\s*-\s*([A-Za-z0-9_./]{5,32})(?:\s+(.*))?$/);
                            if (m) {
                                var qtyText = (m[1] || '').replace(/\s+/g, '').trim();
                                var code = (m[2] || '').trim();
                                var desc = (m[3] || '').trim();
                                if (code && !seen[code]) {
                                    seen[code] = true;
                                    items.push({ code: code, qtyText: qtyText, description: desc });
                                }
                            }
                        }
                    }

                    function inspectDoc(doc) {
                        if (!doc || !doc.body) return;
                        try {
                            var norm = (doc.body.innerText || doc.body.textContent || '')
                                .replace(/[\s\u00A0]+/g, ' ')
                                .toLowerCase()
                                .replace(/\u0307/g, '');
                            if (norm.includes('eksik malzeme listesi') || (norm.includes('ileti metni') && norm.includes('iletitp'))) {
                                foundEksikScreen = true;
                                var tds = doc.querySelectorAll('td');
                                for (var i = 0; i < tds.length; i++) {
                                    if (tds[i].querySelector('table')) continue;
                                    parseLines(tds[i].innerText || tds[i].textContent || '');
                                }
                                parseLines(doc.body.innerText || doc.body.textContent || '');
                                var fields = doc.querySelectorAll('input[type="text"], textarea');
                                for (var j = 0; j < fields.length; j++) {
                                    parseLines(fields[j].value || '');
                                }
                            }
                        } catch (_) {}
                    }

                    inspectDoc(document);
                    for (var f3 = 0; f3 < frames.length; f3++) {
                        try {
                            inspectDoc(frames[f3].contentDocument);
                        } catch (_) {}
                    }

                    if (foundEksikScreen && items.length > 0 && window.AndroidBridge && window.AndroidBridge.onEksikCodesDetected) {
                        window.AndroidBridge.onEksikCodesDetected(JSON.stringify(items));
                    }
                })();
            }

            evaluatePage();
            setTimeout(evaluatePage, 200);
            setTimeout(evaluatePage, 600);
            setTimeout(evaluatePage, 1200);
        })();
    """.trimIndent()
    wv.evaluateJavascript(js, null)
  }

  fun autoFillForm(wv: WebView) {
    processPage(wv, forceFill = true)
  }

  // System back press handles WebView navigation history first
  BackHandler(enabled = canGoBack && !showXxSheet) {
    webViewRef?.goBack()
  }

  Box(modifier = Modifier.fillMaxSize()) {
  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .statusBarsPadding()
          .background(MaterialTheme.colorScheme.surface)
      ) {
        // Top Header Bar with branding & action buttons
        Surface(
          tonalElevation = 3.dp,
          shadowElevation = 2.dp,
          color = MaterialTheme.colorScheme.surface
        ) {
          Column {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              // Brand & Domain
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                  .weight(1f)
                  .clickable {
                    webViewRef?.loadUrl(TARGET_URL)
                  }
              ) {
                Box(
                  modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(NavyPrimary),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.Public,
                    contentDescription = stringResource(R.string.icon_desc_app),
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                  )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                      imageVector = Icons.Default.Lock,
                      contentDescription = stringResource(R.string.icon_desc_secure),
                      tint = Color(0xFF10B981),
                      modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                      text = pageTitle.ifBlank { defaultPageTitle },
                      style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                      ),
                      maxLines = 1,
                      overflow = TextOverflow.Ellipsis
                    )
                  }
                  Text(
                    text = currentUrl.removePrefix("https://").removePrefix("http://"),
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                      fontSize = 11.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                  )
                }
              }

              // Quick Actions (Key Auto-Fill, Zoom Badge & Menu)
              Row(verticalAlignment = Alignment.CenterVertically) {

                // Auto Fill Quick Button
                IconButton(
                  onClick = {
                    webViewRef?.let { autoFillForm(it) }
                  },
                  modifier = Modifier.testTag("autofill_button")
                ) {
                  Icon(
                    imageVector = Icons.Default.Key,
                    contentDescription = stringResource(R.string.btn_autofill_desc),
                    tint = AmberAccent
                  )
                }

                Surface(
                  shape = RoundedCornerShape(16.dp),
                  color = MaterialTheme.colorScheme.primaryContainer,
                  modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { showZoomSheet = true }
                    .testTag("zoom_badge_button")
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Icon(
                      imageVector = Icons.Default.ZoomIn,
                      contentDescription = stringResource(R.string.zoom_in_desc),
                      tint = MaterialTheme.colorScheme.onPrimaryContainer,
                      modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                      text = "${zoomPercent}%",
                      style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                      )
                    )
                  }
                }

                Spacer(modifier = Modifier.width(4.dp))

                Box {
                  IconButton(
                    onClick = { showMoreMenu = true },
                    modifier = Modifier.testTag("more_menu_button")
                  ) {
                    Icon(
                      imageVector = Icons.Default.MoreVert,
                      contentDescription = stringResource(R.string.btn_more_options_desc)
                    )
                  }

                  DropdownMenu(
                    expanded = showMoreMenu,
                    onDismissRequest = { showMoreMenu = false }
                  ) {
                    DropdownMenuItem(
                      text = { Text(stringResource(R.string.menu_autofill)) },
                      leadingIcon = { Icon(Icons.Default.Key, contentDescription = null, tint = AmberAccent) },
                      onClick = {
                        showMoreMenu = false
                        webViewRef?.let { autoFillForm(it) }
                      }
                    )
                    DropdownMenuItem(
                      text = { Text(stringResource(R.string.menu_zoom, zoomPercent)) },
                      leadingIcon = { Icon(Icons.Default.AspectRatio, contentDescription = null) },
                      onClick = {
                        showMoreMenu = false
                        showZoomSheet = true
                      }
                    )
                    DropdownMenuItem(
                      text = { Text(stringResource(R.string.menu_language)) },
                      leadingIcon = { Icon(Icons.Default.Language, contentDescription = null) },
                      onClick = {
                        showMoreMenu = false
                        showLanguageMenu = true
                      }
                    )
                    DropdownMenuItem(
                      text = {
                        Text(
                          if (isDesktopMode) stringResource(R.string.menu_mobile_mode)
                          else stringResource(R.string.menu_desktop_mode)
                        )
                      },
                      leadingIcon = {
                        Icon(
                          if (isDesktopMode) Icons.Default.Smartphone else Icons.Default.Computer,
                          contentDescription = null
                        )
                      },
                      onClick = {
                        showMoreMenu = false
                        isDesktopMode = !isDesktopMode
                        webViewRef?.let { wv ->
                          if (isDesktopMode) {
                            wv.settings.userAgentString =
                              "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36"
                          } else {
                            wv.settings.userAgentString = null
                          }
                          wv.reload()
                        }
                      }
                    )
                    DropdownMenuItem(
                      text = { Text(stringResource(R.string.menu_clear_cache)) },
                      leadingIcon = { Icon(Icons.Default.CleaningServices, contentDescription = null) },
                      onClick = {
                        showMoreMenu = false
                        webViewRef?.clearCache(true)
                        CookieManager.getInstance().removeAllCookies(null)
                        webViewRef?.reload()
                      }
                    )
                  }
                }
              }
            }

            // Compact Navigation Bar (Back, Forward, Center Quick Autofill, Home, Refresh)
            val totalEksikCount = remember(eksikItems) {
              eksikItems.sumOf { it.totalQty.coerceAtLeast(1) }
            }
            val usedEksikCount = remember(eksikItems) {
              eksikItems.sumOf { it.scannedCount.coerceIn(0, it.totalQty.coerceAtLeast(1)) }
            }
            val unusedEksikCount = (totalEksikCount - usedEksikCount).coerceAtLeast(0)
            val navSideWeight = if (totalEksikCount > 0) 0.78f else 1f
            val navCenterWeight = if (totalEksikCount > 0) 1.88f else 1f

            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier.weight(navSideWeight),
                contentAlignment = Alignment.Center
              ) {
                IconButton(
                  onClick = { webViewRef?.goBack() },
                  enabled = canGoBack,
                  modifier = Modifier.testTag("nav_back_button")
                ) {
                  Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.nav_back_desc)
                  )
                }
              }

              Box(
                modifier = Modifier.weight(navSideWeight),
                contentAlignment = Alignment.Center
              ) {
                IconButton(
                  onClick = { webViewRef?.goForward() },
                  enabled = canGoForward,
                  modifier = Modifier.testTag("nav_forward_button")
                ) {
                  Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = stringResource(R.string.nav_forward_desc)
                  )
                }
              }

              // Center Distinctive Quick Autofill Button + Used/Unused/Total Counts
              Box(
                modifier = Modifier.weight(navCenterWeight),
                contentAlignment = Alignment.Center
              ) {
                Surface(
                  onClick = {
                    val wv = webViewRef
                    eksikSearchQuery = ""
                    if (wv != null) {
                      refreshEksikFromWebView(wv) {
                        showXxSheet = true
                      }
                    } else {
                      showXxSheet = true
                    }
                  },
                  shape = RoundedCornerShape(16.dp),
                  color = AmberAccent,
                  shadowElevation = 4.dp,
                  modifier = Modifier
                    .then(
                      if (totalEksikCount > 0) {
                        Modifier.height(42.dp)
                      } else {
                        Modifier.size(width = 56.dp, height = 42.dp)
                      }
                    )
                    .testTag("xx_quick_button")
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = if (totalEksikCount > 0) 8.dp else 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                  ) {
                    Icon(
                      imageVector = Icons.Default.AutoFixHigh,
                      contentDescription = stringResource(R.string.btn_xx_scan_desc),
                      tint = Color.Black,
                      modifier = Modifier.size(20.dp)
                    )
                    if (totalEksikCount > 0) {
                      Spacer(modifier = Modifier.width(6.dp))
                      Surface(
                        color = Color(0xFF0F172A),
                        shape = RoundedCornerShape(10.dp)
                      ) {
                        Row(
                          modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                          verticalAlignment = Alignment.CenterVertically,
                          horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                          Text(
                            text = String.format(Locale.US, "%d", usedEksikCount),
                            color = Color(0xFF4ADE80),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace,
                            maxLines = 1
                          )
                          Text(
                            text = String.format(Locale.US, "%d", unusedEksikCount),
                            color = Color(0xFFF87171),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace,
                            maxLines = 1
                          )
                          Text(
                            text = String.format(Locale.US, "%d", totalEksikCount),
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace,
                            maxLines = 1
                          )
                        }
                      }
                    }
                  }
                }
              }

              Box(
                modifier = Modifier.weight(navSideWeight),
                contentAlignment = Alignment.Center
              ) {
                IconButton(
                  onClick = {
                    hasError = false
                    webViewRef?.loadUrl(TARGET_URL)
                  },
                  modifier = Modifier.testTag("nav_home_button")
                ) {
                  Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(R.string.nav_home_desc),
                    tint = MaterialTheme.colorScheme.primary
                  )
                }
              }

              Box(
                modifier = Modifier.weight(navSideWeight),
                contentAlignment = Alignment.Center
              ) {
                IconButton(
                  onClick = {
                    hasError = false
                    webViewRef?.reload()
                  },
                  modifier = Modifier.testTag("nav_refresh_button")
                ) {
                  Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(R.string.nav_refresh_desc)
                  )
                }
              }
            }
          }
        }

        // Loading Progress Bar
        AnimatedVisibility(
          visible = isLoading,
          enter = fadeIn(),
          exit = fadeOut()
        ) {
          LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
              .fillMaxWidth()
              .height(3.dp),
            color = AmberAccent,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
          )
        }
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(MaterialTheme.colorScheme.background)
    ) {
      if (hasError) {
        // Error / Offline Screen
        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Card(
            colors = CardDefaults.cardColors(
              containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(
              modifier = Modifier
                .padding(28.dp)
                .fillMaxWidth(),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Box(
                modifier = Modifier
                  .size(72.dp)
                  .clip(CircleShape)
                  .background(MaterialTheme.colorScheme.errorContainer),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.WifiOff,
                  contentDescription = stringResource(R.string.error_offline_desc),
                  tint = MaterialTheme.colorScheme.onErrorContainer,
                  modifier = Modifier.size(36.dp)
                )
              }

              Spacer(modifier = Modifier.height(18.dp))

              Text(
                text = stringResource(R.string.error_title),
                style = MaterialTheme.typography.headlineSmall.copy(
                  fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
              )

              Spacer(modifier = Modifier.height(8.dp))

              Text(
                text = errorMessage.ifBlank { stringResource(R.string.error_message) },
                style = MaterialTheme.typography.bodyMedium.copy(
                  color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                ),
                textAlign = TextAlign.Center
              )

              Spacer(modifier = Modifier.height(24.dp))

              Button(
                onClick = {
                  hasError = false
                  webViewRef?.loadUrl(TARGET_URL)
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = NavyPrimary
                ),
                modifier = Modifier.testTag("retry_button")
              ) {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.btn_retry), fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      } else {
        // WebView container
        AndroidView(
          factory = { ctx ->
            PlakaAwareWebView(ctx).apply {
              layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
              )

              @SuppressLint("SetJavaScriptEnabled")
              settings.javaScriptEnabled = true
              settings.domStorageEnabled = true
              settings.databaseEnabled = true
              settings.allowFileAccess = true
              settings.builtInZoomControls = true
              settings.displayZoomControls = false
              settings.useWideViewPort = true
              settings.loadWithOverviewMode = true
              settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE

              // Default Zoom scale for login page
              applyZoom(this, LOGIN_ZOOM_PERCENT)

              addJavascriptInterface(
                object {
                  @JavascriptInterface
                  fun notifyPageState(isLogin: Boolean) {
                    post {
                      val targetZoom = if (isLogin) LOGIN_ZOOM_PERCENT else DEFAULT_PAGE_ZOOM
                      zoomPercent = targetZoom
                      settings.textZoom = targetZoom
                    }
                  }

                  @JavascriptInterface
                  fun setPlakaFocused(focused: Boolean) {
                    updatePlakaFocus(focused)
                  }

                  @JavascriptInterface
                  fun onEksikCodesDetected(itemsJson: String) {
                    val parsed = parseEksikItemsJson(itemsJson)
                    if (parsed.isNotEmpty()) {
                      post {
                        updateEksikFromPage(parsed)
                      }
                    }
                  }
                },
                "AndroidBridge"
              )

              webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                  super.onPageStarted(view, url, favicon)
                  isLoading = true
                  hasError = false
                  isPlakaFocused = false
                  url?.let { currentUrl = it }
                  view?.let {
                    processPage(it)
                  }
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                  super.onPageFinished(view, url)
                  isLoading = false
                  url?.let { currentUrl = it }
                  canGoBack = view?.canGoBack() ?: false
                  canGoForward = view?.canGoForward() ?: false
                  view?.let {
                    processPage(it)
                  }
                }

                override fun onReceivedError(
                  view: WebView?,
                  request: WebResourceRequest?,
                  error: WebResourceError?
                ) {
                  super.onReceivedError(view, request, error)
                  if (request?.isForMainFrame == true) {
                    hasError = true
                    errorMessage = error?.description?.toString() ?: context.getString(R.string.error_generic)
                  }
                }

                override fun shouldOverrideUrlLoading(
                  view: WebView?,
                  request: WebResourceRequest?
                ): Boolean {
                  // Keep navigation inside webview
                  return false
                }
              }

              webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                  super.onProgressChanged(view, newProgress)
                  progress = newProgress / 100f
                  if (newProgress > 40) {
                    view?.let { processPage(it) }
                  }
                  if (newProgress == 100) {
                    isLoading = false
                  }
                }

                override fun onReceivedTitle(view: WebView?, title: String?) {
                  super.onReceivedTitle(view, title)
                  title?.let {
                    if (it.isNotBlank() && !it.contains("s4bayi") && !it.contains("http")) {
                      pageTitle = it
                    }
                  }
                }
              }

              loadUrl(TARGET_URL)
              webViewRef = this
            }
          },
          update = { wv ->
            webViewRef = wv
          },
          modifier = Modifier
            .fillMaxSize()
            .testTag("browser_webview")
        )
      }
    }
  }

  // Zoom Adjuster Bottom Sheet
  if (showZoomSheet) {
    ModalBottomSheet(
      onDismissRequest = { showZoomSheet = false },
      sheetState = sheetState
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 24.dp, vertical = 16.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = stringResource(R.string.zoom_sheet_title),
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold
            )
          )

          Text(
            text = "${zoomPercent}%",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
              .background(
                MaterialTheme.colorScheme.primaryContainer,
                RoundedCornerShape(8.dp)
              )
              .padding(horizontal = 10.dp, vertical = 4.dp)
          )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Preset Chips in two balanced rows so all buttons are wide and horizontal
        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          val row1 = listOf(100, 125, 150)
          val row2 = listOf(200, 250, 300)

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            row1.forEach { preset ->
              val selected = zoomPercent == preset
              FilterChip(
                modifier = Modifier.weight(1f),
                selected = selected,
                onClick = {
                  zoomPercent = preset
                  webViewRef?.let { applyZoom(it, preset) }
                },
                label = {
                  Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(
                      text = "${preset}%",
                      fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                      maxLines = 1,
                      textAlign = TextAlign.Center
                    )
                  }
                },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = NavyPrimary,
                  selectedLabelColor = Color.White
                )
              )
            }
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            row2.forEach { preset ->
              val selected = zoomPercent == preset
              FilterChip(
                modifier = Modifier.weight(1f),
                selected = selected,
                onClick = {
                  zoomPercent = preset
                  webViewRef?.let { applyZoom(it, preset) }
                },
                label = {
                  Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(
                      text = "${preset}%",
                      fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                      maxLines = 1,
                      textAlign = TextAlign.Center
                    )
                  }
                },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = NavyPrimary,
                  selectedLabelColor = Color.White
                )
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Slider Control
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.fillMaxWidth()
        ) {
          IconButton(
            onClick = {
              if (zoomPercent > 50) {
                zoomPercent -= 10
                webViewRef?.let { applyZoom(it, zoomPercent) }
              }
            }
          ) {
            Icon(Icons.Default.ZoomOut, contentDescription = stringResource(R.string.zoom_out_desc))
          }

          Slider(
            value = zoomPercent.toFloat(),
            onValueChange = { newValue ->
              zoomPercent = newValue.toInt()
              webViewRef?.let { applyZoom(it, zoomPercent) }
            },
            valueRange = 50f..400f,
            steps = 34,
            modifier = Modifier.weight(1f)
          )

          IconButton(
            onClick = {
              if (zoomPercent < 400) {
                zoomPercent += 10
                webViewRef?.let { applyZoom(it, zoomPercent) }
              }
            }
          ) {
            Icon(Icons.Default.ZoomIn, contentDescription = stringResource(R.string.zoom_in_desc))
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End
        ) {
          OutlinedButton(
            onClick = {
              zoomPercent = DEFAULT_PAGE_ZOOM
              webViewRef?.let { applyZoom(it, DEFAULT_PAGE_ZOOM) }
            }
          ) {
            Text(stringResource(R.string.btn_reset_zoom))
          }

          Spacer(modifier = Modifier.width(8.dp))

          Button(
            onClick = {
              scope.launch { sheetState.hide() }.invokeOnCompletion {
                showZoomSheet = false
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
          ) {
            Text(stringResource(R.string.btn_done))
          }
        }

        Spacer(modifier = Modifier.height(24.dp))
      }
    }
  }

  // Fixed Full-Screen Overlay inside Activity window (avoids Dialog magnifier/IME freeze and closes only via X or valid code entry)
  if (showXxSheet) {
    EksikFullScreenOverlay(
      eksikItems = eksikItems,
      searchQuery = eksikSearchQuery,
      onSearchQueryChange = { eksikSearchQuery = it },
      onClearAll = { saveEksikItems(emptyList()) },
      onClose = { showXxSheet = false },
      onTapItem = { code ->
        submitXxBarcodeToWebView(
          wv = webViewRef,
          rawProductCode = code,
          pressEnter = true,
          onSubmitted = { showXxSheet = false }
        )
      },
      onLongPressItem = { code ->
        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
        copyXxBarcodeOnly(code)
      }
    )
  }

  // Language Selection Dialog
  if (showLanguageMenu) {
    AlertDialog(
      onDismissRequest = { showLanguageMenu = false },
      title = { Text(stringResource(R.string.menu_language)) },
      text = {
        Column {
          LanguageOption(stringResource(R.string.lang_en), "en") {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
            showLanguageMenu = false
          }
          LanguageOption(stringResource(R.string.lang_ar), "ar") {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ar"))
            showLanguageMenu = false
          }
          LanguageOption(stringResource(R.string.lang_tr), "tr") {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("tr"))
            showLanguageMenu = false
          }
        }
      },
      confirmButton = {
        TextButton(onClick = { showLanguageMenu = false }) {
          Text(stringResource(R.string.btn_done))
        }
      }
    )
  }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EksikFullScreenOverlay(
  eksikItems: List<EksikProductItem>,
  searchQuery: String,
  onSearchQueryChange: (String) -> Unit,
  onClearAll: () -> Unit,
  onClose: () -> Unit,
  onTapItem: (String) -> Unit,
  onLongPressItem: (String) -> Unit
) {
  // Only X button or valid barcode entry closes this overlay
  BackHandler(enabled = true) { }

  val context = LocalContext.current
  val focusManager = LocalFocusManager.current
  val backdropInteraction = remember { MutableInteractionSource() }
  val searchHint = stringResource(R.string.xx_eksik_search_hint)
  val currentSearchQuery by rememberUpdatedState(searchQuery)
  val currentOnSearchQueryChange by rememberUpdatedState(onSearchQueryChange)

  var isSearchFocused by remember { mutableStateOf(false) }
  var searchEditText by remember { mutableStateOf<AppCompatEditText?>(null) }

  val textColorArgb = MaterialTheme.colorScheme.onSurface.toArgb()
  val hintColorArgb = MaterialTheme.colorScheme.onSurfaceVariant.toArgb()
  val borderColor = if (isSearchFocused) {
    MaterialTheme.colorScheme.primary
  } else {
    MaterialTheme.colorScheme.outline
  }

  fun hideSearchKeyboard() {
    focusManager.clearFocus()
    searchEditText?.let { et ->
      et.clearFocus()
      val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
      imm?.hideSoftInputFromWindow(et.windowToken, 0)
    }
  }

  val totalCount = remember(eksikItems) {
    eksikItems.sumOf { it.totalQty.coerceAtLeast(1) }
  }
  val usedCount = remember(eksikItems) {
    eksikItems.sumOf { it.scannedCount.coerceIn(0, it.totalQty.coerceAtLeast(1)) }
  }
  val unusedCount = (totalCount - usedCount).coerceAtLeast(0)

  val orderedItems = remember(eksikItems, searchQuery) {
    val query = searchQuery.trim().lowercase()
    eksikItems
      .withIndex()
      .sortedWith(compareBy({ it.value.isFullyScanned }, { it.index }))
      .map { it.value }
      .filter { item ->
        query.isEmpty() ||
          item.code.lowercase().contains(query) ||
          item.description.lowercase().contains(query)
      }
  }

  Surface(
    shape = RectangleShape,
    color = MaterialTheme.colorScheme.surface,
    modifier = Modifier
      .fillMaxSize()
      .zIndex(10f)
      .clickable(
        interactionSource = backdropInteraction,
        indication = null,
        onClick = {}
      )
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
        .imePadding()
        .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
      // Search Bar + Delete + Close Button
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          shape = RoundedCornerShape(14.dp),
          color = Color.Transparent,
          border = BorderStroke(if (isSearchFocused) 2.dp else 1.dp, borderColor),
          modifier = Modifier
            .weight(1f)
            .defaultMinSize(minHeight = 52.dp)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) {
              searchEditText?.let { et ->
                et.requestFocus()
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
              }
            }
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Search,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.padding(start = 12.dp, end = 8.dp)
            )

            AndroidView(
              factory = { ctx ->
                AppCompatEditText(ctx).apply {
                  layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                  )
                  background = null
                  setPadding(0, 0, 0, 0)
                  setSingleLine(true)
                  setHorizontallyScrolling(true)
                  maxLines = 1
                  isHorizontalScrollBarEnabled = false
                  setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                  setTextColor(textColorArgb)
                  setHintTextColor(hintColorArgb)
                  hint = searchHint
                  gravity = Gravity.CENTER_VERTICAL
                  textDirection = View.TEXT_DIRECTION_FIRST_STRONG
                  textAlignment = View.TEXT_ALIGNMENT_VIEW_START
                  inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                  imeOptions = EditorInfo.IME_ACTION_SEARCH or EditorInfo.IME_FLAG_NO_EXTRACT_UI
                  if (currentSearchQuery.isNotEmpty()) {
                    tag = true
                    setText(currentSearchQuery)
                    setSelection(currentSearchQuery.length)
                    tag = false
                  }
                  setOnFocusChangeListener { _, hasFocus ->
                    isSearchFocused = hasFocus
                  }
                  setOnEditorActionListener { v, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                      v.clearFocus()
                      val imm = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                      imm?.hideSoftInputFromWindow(v.windowToken, 0)
                      true
                    } else {
                      false
                    }
                  }
                  addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                      if (tag == true) return
                      val value = s?.toString().orEmpty()
                      if (value != currentSearchQuery) {
                        currentOnSearchQueryChange(value)
                      }
                    }
                  })
                  searchEditText = this
                }
              },
              update = { et ->
                searchEditText = et
                et.setTextColor(textColorArgb)
                et.setHintTextColor(hintColorArgb)
                if (et.hint?.toString() != searchHint) {
                  et.hint = searchHint
                }
                val existing = et.text?.toString().orEmpty()
                if (existing != searchQuery) {
                  et.tag = true
                  et.setText(searchQuery)
                  et.setSelection(searchQuery.length)
                  et.tag = false
                }
              },
              modifier = Modifier
                .weight(1f)
                .padding(vertical = 10.dp)
                .testTag("xx_eksik_search_input")
            )

            if (searchQuery.isNotEmpty()) {
              IconButton(
                onClick = { onSearchQueryChange("") },
                modifier = Modifier.size(48.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.Close,
                  contentDescription = stringResource(R.string.btn_clear_desc)
                )
              }
            } else {
              Spacer(modifier = Modifier.width(12.dp))
            }
          }
        }

        Spacer(modifier = Modifier.width(2.dp))

        if (eksikItems.isNotEmpty()) {
          IconButton(
            onClick = onClearAll,
            modifier = Modifier
              .size(48.dp)
              .testTag("xx_clear_eksik_button")
          ) {
            Icon(
              imageVector = Icons.Default.DeleteOutline,
              contentDescription = stringResource(R.string.xx_clear_eksik_desc),
              modifier = Modifier.size(36.dp)
            )
          }
        }

        IconButton(
          onClick = {
            hideSearchKeyboard()
            onClose()
          },
          modifier = Modifier
            .size(48.dp)
            .testTag("xx_close_dialog_button")
        ) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(R.string.btn_close_desc),
            modifier = Modifier.size(36.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(4.dp))

      if (eksikItems.isNotEmpty()) {
        // Large English-Digit Stats Bar (Numbers Only): Used (Green), Unused (Red), Total (White)
        Surface(
          color = Color(0xFF0F172A),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("xx_counts_bar")
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = String.format(Locale.US, "%d", usedCount),
              color = Color(0xFF4ADE80),
              fontSize = 22.sp,
              fontWeight = FontWeight.ExtraBold,
              fontFamily = FontFamily.Monospace,
              maxLines = 1
            )
            Text(
              text = String.format(Locale.US, "%d", unusedCount),
              color = Color(0xFFF87171),
              fontSize = 22.sp,
              fontWeight = FontWeight.ExtraBold,
              fontFamily = FontFamily.Monospace,
              maxLines = 1
            )
            Text(
              text = String.format(Locale.US, "%d", totalCount),
              color = Color.White,
              fontSize = 22.sp,
              fontWeight = FontWeight.ExtraBold,
              fontFamily = FontFamily.Monospace,
              maxLines = 1
            )
          }
        }

        Spacer(modifier = Modifier.height(6.dp))

        LazyColumn(
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
          verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
          items(
            items = orderedItems,
            key = { it.code }
          ) { item ->
            val isDone = item.isFullyScanned
            val displayQty = if (isDone) {
              item.totalQty
            } else if (item.scannedCount > 0) {
              item.remainingQty
            } else {
              item.totalQty
            }
            val isMultiQty = !isDone && displayQty > 1
            val countLabel = formatQtyBadge(displayQty, item.qtyText)

            Card(
              colors = CardDefaults.cardColors(
                containerColor = if (isMultiQty) {
                  AmberAccent.copy(alpha = 0.22f)
                } else {
                  MaterialTheme.colorScheme.surfaceVariant
                }
              ),
              border = if (isMultiQty) BorderStroke(2.5.dp, AmberAccent) else null,
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("eksik_item_${item.code}")
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .defaultMinSize(minHeight = 48.dp)
                  .combinedClickable(
                    onClick = { onTapItem(item.code) },
                    onLongClick = { onLongPressItem(item.code) }
                  )
                  .padding(horizontal = 8.dp, vertical = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = item.code,
                    style = MaterialTheme.typography.titleMedium.copy(
                      fontSize = 19.sp,
                      lineHeight = 22.sp,
                      fontWeight = FontWeight.ExtraBold,
                      fontFamily = FontFamily.Monospace,
                      textDirection = TextDirection.Ltr,
                      color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                  )
                  if (item.description.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(1.dp))
                    Text(
                      text = item.description,
                      style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 13.5.sp,
                        lineHeight = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.78f)
                      ),
                      maxLines = 1,
                      overflow = TextOverflow.Ellipsis
                    )
                  }
                }

                Spacer(modifier = Modifier.width(6.dp))

                Surface(
                  color = if (isDone) Color(0xFF16A34A) else Color(0xFFDC2626),
                  border = if (isMultiQty) BorderStroke(2.dp, Color(0xFFFDE047)) else null,
                  shape = RoundedCornerShape(8.dp)
                ) {
                  Text(
                    text = countLabel,
                    style = MaterialTheme.typography.labelLarge.copy(
                      fontSize = if (isMultiQty) 16.sp else 15.sp,
                      fontWeight = if (isMultiQty) FontWeight.Black else FontWeight.ExtraBold,
                      textDirection = TextDirection.Ltr,
                      color = Color.White
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                  )
                }
              }
            }
          }
        }
      } else {
        Spacer(modifier = Modifier.weight(1f))
      }
    }
  }
}

@Composable
fun LanguageOption(label: String, tag: String, onClick: () -> Unit) {
  val currentLocales = AppCompatDelegate.getApplicationLocales()
  val isSelected = if (tag.isEmpty()) currentLocales.isEmpty else currentLocales.toLanguageTags().startsWith(tag)

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .padding(vertical = 12.dp, horizontal = 8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.bodyLarge,
      modifier = Modifier.weight(1f),
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
      color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
    )
    if (isSelected) {
      Icon(
        imageVector = Icons.Default.CheckCircle,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(20.dp)
      )
    }
  }
}
