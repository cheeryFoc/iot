export default function tools () {
    const signSetItem = localStorage.setItem;
    localStorage.setItem = function (key, newValue) {
      let setEvent = new Event('setItemEvent');
      setEvent.key = key;
      setEvent.newValue = newValue;
      window.dispatchEvent(setEvent);
      signSetItem.apply(this, arguments);
    };
  }