
export function scanDescription(scan) {
  if (!scan) {
    return ""
  } else if (!scan.brand && !scan.label) {
    return "";
  } else if (!scan.brand) {
    return scan.label;
  } else if (!scan.label) {
    return scan.brand;
  } else {
    return `${ scan.brand.trim() } ${ scan.label.trim() }`
  }
}
