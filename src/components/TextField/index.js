import React from "react";
import TextAreaAutoSize from "react-textarea-autosize";
import "./text-field.css";

export default function TextField({
  name,
  value,
  onChange,
  placeholder,
  className,
  deleteList,
  handleCancel,
  onEnter
}) {
  return (
    <div className="list-title-edit">
      <TextAreaAutoSize
        autoFocus
        className={className}
        name={name}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        style={{ width: deleteList ? 220 : 354 }}
        onKeyDown={onEnter}
      />
      {deleteList && (
        <>
          <ion-icon name='trash-outline' onClick={deleteList}></ion-icon>
          <ion-icon name='close' onClick={handleCancel}></ion-icon>
        </>
      )}
    </div>
  );
}
