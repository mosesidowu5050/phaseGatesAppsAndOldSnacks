import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class DiaryTest {

    Diary diary;

    @Before
    public void setUp() {
        diary = new Diary("jonsnow", "jonsnow1");
    }

    @Test
    public void unlockDiaryTest() {
        diary.unlockDiary("jonsnow1");
        boolean isLock = diary.isLocked();
        assertFalse(isLock);
    }

    @Test
    public void unlockDiaryWIthWrongPasswordTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            diary.unlockDiary("jonsnow110");
        });
    }

    @Test
    public void lockDiaryTest() {
        diary.lockDiary();
        assertTrue(diary.isLocked());
    }

    @Test
    public void unlockDiary_createEntry_checkSizeOfDiaryTest() {
        diary.unlockDiary("jonsnow1");
        diary.createEntry("Desiderata", "Go placidly amidst the noise and haste");
        assertEquals(1, diary.getSize());
        Entry entryDate = diary.findEntryById(400);
        assertEquals(LocalDateTime.now(), entryDate.getDateCreated());
        diary.lockDiary();
        assertTrue(diary.isLocked());
    }

    @Test
    public void createEntryWithWrongDetailsThrowExceptionsTest() {
        diary.unlockDiary("jonsnow1");
        assertThrows(IllegalArgumentException.class, () -> {
            diary.createEntry(null, null);
        });
    }

    @Test
    public void unlockDiary_createEntry_checkDiaryIdNumber_lockDiaryTest() {
        diary.unlockDiary("jonsnow1");
        diary.createEntry("Desiderata", "Go placidly amidst the noise and haste");
        assertEquals(400, diary.getId());
        diary.lockDiary();
        assertTrue(diary.isLocked());
    }

    @Test
    public void unlockDiary_createTwoEntry_checkDiaryIdNumber_lockDiaryTest() {
        diary.unlockDiary("jonsnow1");
        diary.createEntry("Desiderata", "Go placidly amidst the noise and haste");

        assertEquals(400, diary.getId());
        diary.createEntry("Desiderata", "Go placidly amidst the noise and haste");
        assertEquals(401, diary.getId());
        diary.lockDiary();
        assertTrue(diary.isLocked());
    }

    @Test
    public void unlockDiary_createTwoEntry_deleteEntryWithDiaryIdNumber_lockDiaryTest() {
        diary.unlockDiary("jonsnow1");
        diary.createEntry("Desiderata", "Go placidly amidst the noise and haste");
        diary.createEntry("Equipment", "Figure it out for yourself my lad");
        diary.deleteEntry(400);
        assertEquals(1, diary.getSize());
        diary.lockDiary();
        assertTrue(diary.isLocked());
    }

    @Test
    public void unlockDiary_createTwoEntry_deleteEntryWithInvalidIdNumber_lockDiary() {
        assertThrows(IllegalArgumentException.class, () -> {
            diary.createEntry("Desiderata", "Go placidly amidst the noise and haste");
            diary.createEntry("Equipment", "Figure it out for yourself my lad");
            diary.deleteEntry(403);
        });
    }

    @Test
    public void unlockDiary_createOneEntry_findEntryByIdNumberTest() {
        diary.unlockDiary("jonsnow1");
        diary.createEntry("Desiderata", "Go placidly amidst the noise and haste");
        diary.createEntry("Equipment", "Figure it out for yourself my lad");
        Entry foundEntry = diary.findEntryById(400);
        assertEquals("Desiderata", foundEntry.getTitle());
        assertEquals("Go placidly amidst the noise and haste", foundEntry.getContentBody());
        assertFalse(diary.isLocked());
    }

    @Test
    public void unlockDiary_createTwoEntry_findEntryByIdNumber_lockDiaryTest() {
        diary.unlockDiary("jonsnow1");
        diary.createEntry("1. Desiderata", "Go placidly amidst the noise and haste");
        diary.createEntry("Equipment", "Figure it out for yourself my lad");
        Entry foundEntry = diary.findEntryById(401);
        assertEquals("Equipment", foundEntry.getTitle());
        assertEquals("Figure it out for yourself my lad", foundEntry.getContentBody());
        diary.lockDiary();
        assertTrue(diary.isLocked());
    }

    @Test
    public void unlockDiary_createEntry_updateEntryTest() {
        diary.unlockDiary("jonsnow1");
        diary.createEntry("Desiderata", "Go placidly amidst the noise and haste");
        diary.updateEntry(400, "Equipment", "Figure it out for yourself my lad");
        Entry foundEntry = diary.findEntryById(400);
        assertEquals("Equipment", foundEntry.getTitle());
        assertEquals("Figure it out for yourself my lad", foundEntry.getContentBody());
    }

    @Test
    public void unlockDiary_createEntry_updateEntryWithInvalidDetails_throwsExceptionTest() {
        diary.unlockDiary("jonsnow1");
        assertThrows(IllegalArgumentException.class, () -> {
            diary.updateEntry(400, null, null);
            diary.updateEntry(400, "Equipment", "Figure it out for yourself my lad");
        });
    }




    @Test
    public void testAdd_ValidCredentials_AddsDiary() {
        Diaries diaries = new Diaries();
        diaries.add("alice", "password123");
        Diary found = diaries.findByUsername("alice");
        assertNotNull(found);
        assertEquals("alice", found.getUsername());
        assertTrue(found.checkPassword("password123"));
    }

    @Test
    public void testAdd_DuplicateUsername_ThrowsException() {
        Diaries diaries = new Diaries();
        diaries.add("alice", "password123");
        assertThrows(IllegalArgumentException.class, () ->
                diaries.add("alice", "password123")
        );
    }

    @Test
    public void testAdd_InvalidUsername_ThrowsException() {
        Diaries diaries = new Diaries();
        assertThrows( IllegalArgumentException.class,() ->
                diaries.add("", "password123")
        );
    }

    @Test
    public void testAdd_ShortPassword_ThrowsException() {
        Diaries diaries = new Diaries();
        assertThrows(IllegalArgumentException.class, () -> diaries.add("alice", "short")
        );
    }

    @Test
    public void testFindByUsername_ExistingUsername_ReturnsDiary() {
        Diaries diaries = new Diaries();
        diaries.add("alice", "password123");
        Diary found = diaries.findByUsername("alice");
        assertNotNull(found);
        assertEquals("alice", found.getUsername());
    }
    @Test
    public void testDelete_ValidCredentials_RemovesDiary() {
        Diaries diaries = new Diaries();
        diaries.add("alice", "password123");
        diaries.delete("alice", "password123");
        Diary found = diaries.findByUsername("alice");
        assertNull(found);
    }
    @Test
    public void testDelete_NonExistingUsername_ThrowsException() {
        Diaries diaries = new Diaries();
        assertThrows(IllegalArgumentException.class, () ->
                        diaries.delete("alice", "password123")
        );
    }
}
